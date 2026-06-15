/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.test
 * File: OrderCreationServiceImplTest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:51:59
 */

package com.nthn.storevision.service.order.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nthn.storevision.constant.voucher.DiscountType;
import com.nthn.storevision.constant.order.OrderChannel;
import com.nthn.storevision.constant.order.OrderStatus;
import com.nthn.storevision.dto.order.OrderDTO;
import com.nthn.storevision.dto.order_item.OrderItemDTO;
import com.nthn.storevision.dto.voucher.VoucherDTO;
import com.nthn.storevision.dto.request.OrderCreationRequest;
import com.nthn.storevision.dto.request.OrderItemCreationRequest;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.exception.InsufficientStockException;
import com.nthn.storevision.mapper.OrderMapper;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.customer.CustomerRetrievalService;
import com.nthn.storevision.service.orderitem.OrderItemCreationService;
import com.nthn.storevision.service.product.ProductRetrievalService;
import com.nthn.storevision.service.voucher.VoucherValidationService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for {@link OrderCreationServiceImpl}. */
@ExtendWith(MockitoExtension.class)
class OrderCreationServiceImplTest {

  private static final Long CUSTOMER_ID = 1L;
  private static final Long PRODUCT_ID = 10L;
  private static final int QUANTITY = 2;
  private static final BigDecimal SUBTOTAL = new BigDecimal("200000");
  private static final BigDecimal TAX_AMOUNT = new BigDecimal("20000.00");
  private static final BigDecimal TAX_RATE = new BigDecimal("0.10");
  private static final BigDecimal UNIT_PRICE = new BigDecimal("100000");
  @Mock private CustomerRetrievalService customerRetrievalService;
  @Mock private OrderMapper mapper;
  @Mock private OrderItemCreationService orderItemCreationService;
  @Mock private ProductRetrievalService productRetrievalService;
  @Mock private OrderRepository repository;
  private OrderCreationServiceImpl service;
  @Mock private VoucherValidationService voucherValidationService;

  @Test
  void createOrder_shouldApplyFixedAmountDiscount() {
    final CustomerEntity customer = activeCustomer();
    final ProductEntity product = activeProduct(50);
    when(customerRetrievalService.<CustomerEntity>findById(eq(CUSTOMER_ID), any()))
        .thenReturn(customer);
    when(productRetrievalService.<ProductEntity>findById(eq(PRODUCT_ID), any()))
        .thenReturn(product);

    final VoucherDTO voucher =
        VoucherDTO.builder()
            .id(6L)
            .code("FIX15K")
            .discountType(DiscountType.FIXED_AMOUNT)
            .discountValue(new BigDecimal("15000"))
            .build();
    when(voucherValidationService.validate("FIX15K", SUBTOTAL)).thenReturn(voucher);

    final OrderEntity savedOrder =
        OrderEntity.builder().id(502L).status(OrderStatus.PENDING).build();
    final OrderDTO mappedOrder = OrderDTO.builder().id(502L).status(OrderStatus.PENDING).build();
    stubSaveAndOrderItem(savedOrder, mappedOrder);

    service.createOrder(requestWithVoucher("FIX15K"));

    final ArgumentCaptor<OrderEntity> orderCaptor = ArgumentCaptor.forClass(OrderEntity.class);
    verify(repository).save(orderCaptor.capture());
    final OrderEntity orderToSave = orderCaptor.getValue();
    assertThat(orderToSave.getDiscountAmount()).isEqualByComparingTo(new BigDecimal("15000"));
    assertThat(orderToSave.getTotalAmount()).isEqualByComparingTo(new BigDecimal("205000.00"));
  }

  @Test
  void createOrder_shouldCalculateTotalsAndPersistOrder_whenNoVoucherProvided() {
    final CustomerEntity customer = activeCustomer();
    final ProductEntity product = activeProduct(50);
    when(customerRetrievalService.<CustomerEntity>findById(eq(CUSTOMER_ID), any()))
        .thenReturn(customer);
    when(productRetrievalService.<ProductEntity>findById(eq(PRODUCT_ID), any()))
        .thenReturn(product);

    final OrderEntity savedOrder =
        OrderEntity.builder().id(500L).status(OrderStatus.PENDING).build();
    final OrderDTO mappedOrder = OrderDTO.builder().id(500L).status(OrderStatus.PENDING).build();
    stubSaveAndOrderItem(savedOrder, mappedOrder);

    final OrderDTO result = service.createOrder(requestWithVoucher(null));

    final ArgumentCaptor<OrderEntity> orderCaptor = ArgumentCaptor.forClass(OrderEntity.class);
    verify(repository).save(orderCaptor.capture());
    final OrderEntity orderToSave = orderCaptor.getValue();
    assertThat(orderToSave.getCustomer()).isEqualTo(customer);
    assertThat(orderToSave.getStatus()).isEqualTo(OrderStatus.PENDING);
    assertThat(orderToSave.getSubtotal()).isEqualByComparingTo(SUBTOTAL);
    assertThat(orderToSave.getDiscountAmount()).isEqualByComparingTo(BigDecimal.ZERO);
    assertThat(orderToSave.getTaxAmount()).isEqualByComparingTo(TAX_AMOUNT);
    assertThat(orderToSave.getTotalAmount()).isEqualByComparingTo(new BigDecimal("220000.00"));
    assertThat(orderToSave.getVoucher()).isNull();

    final ArgumentCaptor<OrderItemDTO> itemCaptor = ArgumentCaptor.forClass(OrderItemDTO.class);
    verify(orderItemCreationService).create(itemCaptor.capture());
    final OrderItemDTO itemToCreate = itemCaptor.getValue();
    assertThat(itemToCreate.getOrderId()).isEqualTo(500L);
    assertThat(itemToCreate.getProductId()).isEqualTo(PRODUCT_ID);
    assertThat(itemToCreate.getUnitPrice()).isEqualByComparingTo(UNIT_PRICE);

    assertThat(result.getId()).isEqualTo(500L);
    assertThat(result.getOrderItems()).hasSize(1);
  }

  @Test
  void createOrder_shouldCapDiscountAtSubtotal_whenFixedAmountExceedsSubtotal() {
    final CustomerEntity customer = activeCustomer();
    final ProductEntity product = activeProduct(50);
    when(customerRetrievalService.<CustomerEntity>findById(eq(CUSTOMER_ID), any()))
        .thenReturn(customer);
    when(productRetrievalService.<ProductEntity>findById(eq(PRODUCT_ID), any()))
        .thenReturn(product);

    final VoucherDTO voucher =
        VoucherDTO.builder()
            .id(7L)
            .code("HUGE")
            .discountType(DiscountType.FIXED_AMOUNT)
            .discountValue(new BigDecimal("500000"))
            .build();
    when(voucherValidationService.validate("HUGE", SUBTOTAL)).thenReturn(voucher);

    final OrderEntity savedOrder =
        OrderEntity.builder().id(503L).status(OrderStatus.PENDING).build();
    final OrderDTO mappedOrder = OrderDTO.builder().id(503L).status(OrderStatus.PENDING).build();
    stubSaveAndOrderItem(savedOrder, mappedOrder);

    service.createOrder(requestWithVoucher("HUGE"));

    final ArgumentCaptor<OrderEntity> orderCaptor = ArgumentCaptor.forClass(OrderEntity.class);
    verify(repository).save(orderCaptor.capture());
    final OrderEntity orderToSave = orderCaptor.getValue();
    // discount cannot exceed the subtotal, so totalAmount cannot go negative
    assertThat(orderToSave.getDiscountAmount()).isEqualByComparingTo(SUBTOTAL);
    assertThat(orderToSave.getTotalAmount()).isEqualByComparingTo(TAX_AMOUNT);
  }

  @Test
  void createOrder_shouldCapPercentageDiscountAtMaxDiscountAmount() {
    final CustomerEntity customer = activeCustomer();
    final ProductEntity product = activeProduct(50);
    when(customerRetrievalService.<CustomerEntity>findById(eq(CUSTOMER_ID), any()))
        .thenReturn(customer);
    when(productRetrievalService.<ProductEntity>findById(eq(PRODUCT_ID), any()))
        .thenReturn(product);

    final VoucherDTO voucher =
        VoucherDTO.builder()
            .id(5L)
            .code("SALE20")
            .discountType(DiscountType.PERCENTAGE)
            .discountValue(new BigDecimal("20"))
            .maxDiscountAmount(new BigDecimal("30000"))
            .build();
    when(voucherValidationService.validate("SALE20", SUBTOTAL)).thenReturn(voucher);

    final OrderEntity savedOrder =
        OrderEntity.builder().id(501L).status(OrderStatus.PENDING).build();
    final OrderDTO mappedOrder = OrderDTO.builder().id(501L).status(OrderStatus.PENDING).build();
    stubSaveAndOrderItem(savedOrder, mappedOrder);

    service.createOrder(requestWithVoucher("SALE20"));

    final ArgumentCaptor<OrderEntity> orderCaptor = ArgumentCaptor.forClass(OrderEntity.class);
    verify(repository).save(orderCaptor.capture());
    final OrderEntity orderToSave = orderCaptor.getValue();
    // 20% of 200000 = 40000, capped at maxDiscountAmount = 30000
    assertThat(orderToSave.getDiscountAmount()).isEqualByComparingTo(new BigDecimal("30000"));
    assertThat(orderToSave.getTotalAmount()).isEqualByComparingTo(new BigDecimal("190000.00"));
    assertThat(orderToSave.getVoucher()).isNotNull();
    assertThat(orderToSave.getVoucher().getId()).isEqualTo(5L);
  }

  @Test
  void createOrder_shouldThrowEntityNotFoundException_whenCustomerIsSoftDeleted() {
    final CustomerEntity customer =
        CustomerEntity.builder().id(CUSTOMER_ID).deletedDate(LocalDateTime.now()).build();
    when(customerRetrievalService.<CustomerEntity>findById(eq(CUSTOMER_ID), any()))
        .thenReturn(customer);

    assertThrows(
        EntityNotFoundException.class, () -> service.createOrder(requestWithVoucher(null)));

    verify(repository, never()).save(any());
  }

  @Test
  void createOrder_shouldThrowEntityNotFoundException_whenProductIsSoftDeleted() {
    final CustomerEntity customer = activeCustomer();
    final ProductEntity product =
        ProductEntity.builder()
            .id(PRODUCT_ID)
            .name("Product A")
            .price(UNIT_PRICE)
            .stockQuantity(50)
            .deletedDate(LocalDateTime.now())
            .build();
    when(customerRetrievalService.<CustomerEntity>findById(eq(CUSTOMER_ID), any()))
        .thenReturn(customer);
    when(productRetrievalService.<ProductEntity>findById(eq(PRODUCT_ID), any()))
        .thenReturn(product);

    assertThrows(
        EntityNotFoundException.class, () -> service.createOrder(requestWithVoucher(null)));

    verify(repository, never()).save(any());
  }

  @Test
  void createOrder_shouldThrowInsufficientStockException_whenStockBelowRequestedQuantity() {
    final CustomerEntity customer = activeCustomer();
    final ProductEntity product = activeProduct(1);
    when(customerRetrievalService.<CustomerEntity>findById(eq(CUSTOMER_ID), any()))
        .thenReturn(customer);
    when(productRetrievalService.<ProductEntity>findById(eq(PRODUCT_ID), any()))
        .thenReturn(product);

    assertThrows(
        InsufficientStockException.class, () -> service.createOrder(requestWithVoucher(null)));

    verify(repository, never()).save(any());
  }

  @BeforeEach
  void setUp() {
    service =
        new OrderCreationServiceImpl(
            mapper,
            repository,
            customerRetrievalService,
            orderItemCreationService,
            productRetrievalService,
            voucherValidationService,
            TAX_RATE);
  }

  private void stubSaveAndOrderItem(final OrderEntity savedOrder, final OrderDTO mappedOrder) {
    when(repository.save(any(OrderEntity.class))).thenReturn(savedOrder);
    when(mapper.toDTO(savedOrder)).thenReturn(mappedOrder);
    when(orderItemCreationService.create(any(OrderItemDTO.class)))
        .thenReturn(
            OrderItemDTO.builder()
                .id(900L)
                .orderId(savedOrder.getId())
                .productId(PRODUCT_ID)
                .quantity(QUANTITY)
                .unitPrice(UNIT_PRICE)
                .build());
  }

  private static CustomerEntity activeCustomer() {
    return CustomerEntity.builder().id(CUSTOMER_ID).build();
  }

  private static ProductEntity activeProduct(final int stockQuantity) {
    return ProductEntity.builder()
        .id(PRODUCT_ID)
        .name("Product A")
        .price(UNIT_PRICE)
        .stockQuantity(stockQuantity)
        .build();
  }

  private static OrderCreationRequest requestWithVoucher(final String voucherCode) {
    return OrderCreationRequest.builder()
        .customerId(CUSTOMER_ID)
        .channel(OrderChannel.POS)
        .items(
            List.of(
                OrderItemCreationRequest.builder()
                    .productId(PRODUCT_ID)
                    .quantity(QUANTITY)
                    .build()))
        .voucherCode(voucherCode)
        .build();
  }
}
