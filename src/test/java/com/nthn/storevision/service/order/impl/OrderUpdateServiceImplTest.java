/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.test
 * File: OrderUpdateServiceImplTest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:40:22
 */

package com.nthn.storevision.service.order.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.nthn.storevision.constant.OrderStatus;
import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.exception.InsufficientStockException;
import com.nthn.storevision.exception.order.InvalidOrderStateException;
import com.nthn.storevision.mapper.OrderMapper;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.customer.LoyaltyService;
import com.nthn.storevision.service.inventory.InventoryCreationService;
import com.nthn.storevision.service.product.ProductUpdateService;
import com.nthn.storevision.service.voucher.VoucherUpdateService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for {@link OrderUpdateServiceImpl}. */
@ExtendWith(MockitoExtension.class)
class OrderUpdateServiceImplTest {

  private static final Long CUSTOMER_ID = 1L;
  private static final Long ORDER_ID = 100L;
  private static final Long PRODUCT_ID = 10L;
  private static final Integer QUANTITY = 2;
  private static final BigDecimal TOTAL_AMOUNT = new BigDecimal("220000.00");
  @Mock private InventoryCreationService inventoryCreationService;
  @Mock private LoyaltyService loyaltyService;
  @Mock private OrderMapper mapper;
  @Mock private ProductUpdateService productUpdateService;
  @Mock private OrderRepository repository;
  private OrderUpdateServiceImpl service;
  @Mock private VoucherUpdateService voucherUpdateService;

  @Test
  void cancel_shouldSetStatusToCancelledAndSave_whenOrderIsPending() {
    final OrderEntity order = pendingOrder(null, 10);
    final OrderDTO mapped = OrderDTO.builder().id(ORDER_ID).status(OrderStatus.CANCELLED).build();
    when(repository.findById(ORDER_ID)).thenReturn(Optional.of(order));
    when(mapper.toDTO(order)).thenReturn(mapped);

    final OrderDTO result = service.cancel(ORDER_ID);

    assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELLED);
    verify(repository).save(order);
    assertThat(result).isEqualTo(mapped);
    verifyNoInteractions(
        productUpdateService, inventoryCreationService, loyaltyService, voucherUpdateService);
  }

  @Test
  void cancel_shouldThrowEntityNotFoundException_whenOrderDoesNotExist() {
    when(repository.findById(ORDER_ID)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> service.cancel(ORDER_ID));

    verify(repository, never()).save(any());
  }

  @Test
  void cancel_shouldThrowEntityNotFoundException_whenOrderIsSoftDeleted() {
    final OrderEntity order =
        OrderEntity.builder()
            .id(ORDER_ID)
            .status(OrderStatus.PENDING)
            .deletedDate(LocalDateTime.now())
            .build();
    when(repository.findById(ORDER_ID)).thenReturn(Optional.of(order));

    assertThrows(EntityNotFoundException.class, () -> service.cancel(ORDER_ID));

    verify(repository, never()).save(any());
  }

  @Test
  void cancel_shouldThrowInvalidOrderStateException_whenOrderIsNotPending() {
    final OrderEntity order =
        OrderEntity.builder().id(ORDER_ID).status(OrderStatus.COMPLETED).build();
    when(repository.findById(ORDER_ID)).thenReturn(Optional.of(order));

    assertThrows(InvalidOrderStateException.class, () -> service.cancel(ORDER_ID));

    verify(repository, never()).save(any());
  }

  @Test
  void
      complete_shouldDeductStockRecordIssueAwardPointsAndIncrementVoucherUsage_whenVoucherPresent() {
    final VoucherEntity voucher = VoucherEntity.builder().id(7L).build();
    final OrderEntity order = pendingOrder(voucher, 10);
    final OrderDTO mapped = OrderDTO.builder().id(ORDER_ID).status(OrderStatus.COMPLETED).build();
    when(repository.findById(ORDER_ID)).thenReturn(Optional.of(order));
    when(mapper.toDTO(order)).thenReturn(mapped);

    final OrderDTO result = service.complete(ORDER_ID);

    assertThat(order.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    verify(productUpdateService).deductStock(PRODUCT_ID, QUANTITY);
    verify(inventoryCreationService).recordIssue(PRODUCT_ID, QUANTITY, ORDER_ID);
    verify(repository).save(order);
    verify(loyaltyService).addPoints(CUSTOMER_ID, TOTAL_AMOUNT, ORDER_ID);
    verify(voucherUpdateService).incrementUsage(7L);
    assertThat(result).isEqualTo(mapped);
  }

  @Test
  void complete_shouldNotIncrementVoucherUsage_whenNoVoucherPresent() {
    final OrderEntity order = pendingOrder(null, 10);
    final OrderDTO mapped = OrderDTO.builder().id(ORDER_ID).status(OrderStatus.COMPLETED).build();
    when(repository.findById(ORDER_ID)).thenReturn(Optional.of(order));
    when(mapper.toDTO(order)).thenReturn(mapped);

    service.complete(ORDER_ID);

    verify(voucherUpdateService, never()).incrementUsage(any());
  }

  @Test
  void complete_shouldThrowInsufficientStockException_whenStockBelowRequestedQuantity() {
    final OrderEntity order = pendingOrder(null, 1);
    when(repository.findById(ORDER_ID)).thenReturn(Optional.of(order));

    assertThrows(InsufficientStockException.class, () -> service.complete(ORDER_ID));

    verify(repository, never()).save(any());
    verify(productUpdateService, never()).deductStock(any(), any());
  }

  @Test
  void complete_shouldThrowInvalidOrderStateException_whenOrderIsNotPending() {
    final OrderEntity order =
        OrderEntity.builder().id(ORDER_ID).status(OrderStatus.CANCELLED).build();
    when(repository.findById(ORDER_ID)).thenReturn(Optional.of(order));

    assertThrows(InvalidOrderStateException.class, () -> service.complete(ORDER_ID));

    verify(repository, never()).save(any());
  }

  @BeforeEach
  void setUp() {
    service =
        new OrderUpdateServiceImpl(
            mapper,
            repository,
            inventoryCreationService,
            loyaltyService,
            productUpdateService,
            voucherUpdateService);
  }

  private static OrderEntity pendingOrder(final VoucherEntity voucher, final int stockQuantity) {
    final ProductEntity product =
        ProductEntity.builder()
            .id(PRODUCT_ID)
            .name("Product A")
            .stockQuantity(stockQuantity)
            .build();
    final OrderItemEntity item =
        OrderItemEntity.builder()
            .id(1L)
            .product(product)
            .quantity(QUANTITY)
            .unitPrice(new BigDecimal("100000"))
            .build();
    final CustomerEntity customer = CustomerEntity.builder().id(CUSTOMER_ID).build();
    return OrderEntity.builder()
        .id(ORDER_ID)
        .status(OrderStatus.PENDING)
        .customer(customer)
        .orderItems(List.of(item))
        .totalAmount(TOTAL_AMOUNT)
        .voucher(voucher)
        .build();
  }
}
