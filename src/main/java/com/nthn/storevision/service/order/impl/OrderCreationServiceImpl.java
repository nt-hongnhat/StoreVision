/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-07-07 13:31:38
 */

package com.nthn.storevision.service.order.impl;

import com.nthn.storevision.component.order.discount.DiscountPolicy;
import com.nthn.storevision.constant.message.CustomerMessageCode;
import com.nthn.storevision.constant.message.ProductMessageCode;
import com.nthn.storevision.constant.order.OrderStatus;
import com.nthn.storevision.dto.order.OrderDTO;
import com.nthn.storevision.dto.order_item.OrderItemDTO;
import com.nthn.storevision.dto.request.OrderCreationRequest;
import com.nthn.storevision.dto.request.OrderItemCreationRequest;
import com.nthn.storevision.dto.voucher.VoucherDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.model.Product;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.customer.CustomerRetrievalService;
import com.nthn.storevision.service.order.OrderCreationService;
import com.nthn.storevision.service.orderitem.OrderItemCreationService;
import com.nthn.storevision.service.product.ProductRetrievalService;
import com.nthn.storevision.service.voucher.VoucherValidationService;
import com.nthn.storevision.util.StockAvailabilityUtil;
import com.nthn.storevision.util.VoucherCodeUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link OrderCreationService} that extends {@link AbstractCreatable} to
 * persist {@link OrderEntity} instances via {@link OrderRepository}.
 *
 * <p>{@link #createOrder} implements the sales-order creation flow: input validation (customer and
 * products must exist and be active), stock availability check without deduction (BR-O01), optional
 * voucher validation (BR-V01–BR-V04), server-side total calculation — {@code totalAmount = subtotal
 * − discount + tax} (BR-O03) — and unit-price lock at creation time (BR-O04). The order and all its
 * items are persisted in a single transaction with status {@link OrderStatus#PENDING}.
 */
@Slf4j
@Service
public class OrderCreationServiceImpl
    extends AbstractCreatable<OrderDTO, OrderEntity, OrderRepository>
    implements OrderCreationService {

  private static final int MONEY_SCALE = 2;
  private final CustomerRetrievalService customerRetrievalService;
  private final List<? extends DiscountPolicy> discountPolicies;
  private final OrderItemCreationService orderItemCreationService;
  private final ProductRetrievalService productRetrievalService;
  private final VoucherValidationService voucherValidationService;
  @Value("${app.order.tax-rate:0.10}")
  private BigDecimal taxRate;

  protected OrderCreationServiceImpl(
      final EntityMapperConfig<OrderDTO, ?, OrderEntity> mapper,
      final OrderRepository repository,
      final CustomerRetrievalService customerRetrievalService,
      final List<? extends DiscountPolicy> discountPolicies,
      final OrderItemCreationService orderItemCreationService,
      final ProductRetrievalService productRetrievalService,
      final VoucherValidationService voucherValidationService) {
    super(mapper, repository);
    this.customerRetrievalService = customerRetrievalService;
    this.discountPolicies = discountPolicies;
    this.orderItemCreationService = orderItemCreationService;
    this.productRetrievalService = productRetrievalService;
    this.voucherValidationService = voucherValidationService;
  }

  /** {@inheritDoc} */
  @Override
  @Transactional
  public OrderDTO createOrder(final @NonNull OrderCreationRequest request) {
    log.debug(
        "Processing order creation request with {} order items and voucher provided={}",
        request.getItems().size(),
        VoucherCodeUtil.isProvided(request.getVoucherCode()));
    try {
      final CustomerEntity customer = findActiveCustomer(request.getCustomerId());
      final Map<Long, Product> products = findActiveProducts(request.getItems());

      StockAvailabilityUtil.verify(request.getItems(), products);

      final BigDecimal subtotal = calculateSubtotal(request.getItems(), products);
      final VoucherDTO voucher = validateVoucherIfPresent(request.getVoucherCode(), subtotal);

      final BigDecimal finalDiscountAmount =
          discountPolicies.stream()
              .map(policy -> policy.calculate(customer, voucher, subtotal))
              .reduce(BigDecimal.ZERO, BigDecimal::max);

      final BigDecimal taxAmount =
          subtotal.multiply(taxRate).setScale(MONEY_SCALE, RoundingMode.HALF_UP);
      final BigDecimal totalAmount = subtotal.subtract(finalDiscountAmount).add(taxAmount);

      final OrderEntity saved =
          getRepository()
              .save(
                  OrderEntity.builder()
                      .customer(customer)
                      .orderDate(LocalDateTime.now())
                      .status(OrderStatus.PENDING)
                      .channel(request.getChannel())
                      .note(request.getNote())
                      .subtotal(subtotal)
                      .discountAmount(finalDiscountAmount)
                      .taxAmount(taxAmount)
                      .totalAmount(totalAmount)
                      .voucher(toVoucherReference(voucher))
                      .build());

      final List<OrderItemDTO> items =
          createOrderItems(saved.getId(), request.getItems(), products);

      final OrderDTO result = getMapper().toDTO(saved);
      result.setOrderItems(items);

      log.debug(
          "Order creation completed successfully with {} persisted order items", items.size());
      return result;
    } catch (final RuntimeException exception) {
      log.error("Order creation processing failed due to an unexpected error", exception);
      throw exception;
    }
  }

  /** BR-O04: each item's unit price is locked from the product's price at creation time. */
  private @NonNull List<OrderItemDTO> createOrderItems(
      final Long orderId,
      final @NonNull List<? extends OrderItemCreationRequest> items,
      final Map<Long, ? extends Product> products) {
    return items.stream()
        .map(
            item ->
                orderItemCreationService.create(
                    OrderItemDTO.builder()
                        .orderId(orderId)
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .unitPrice(products.get(item.getProductId()).getPrice())
                        .build()))
        .toList();
  }

  private @NonNull CustomerEntity findActiveCustomer(final Long customerId) {
    final CustomerEntity customer =
        customerRetrievalService.findById(customerId, Function.identity());
    if (customer.getDeletedDate().isPresent()) {
      throw new EntityNotFoundException(CustomerMessageCode.NOT_FOUND, customerId);
    }
    return customer;
  }

  private @NonNull ProductEntity findActiveProduct(final Long productId) {
    final ProductEntity product = productRetrievalService.findById(productId, Function.identity());
    if (product.getDeletedDate().isPresent()) {
      throw new EntityNotFoundException(ProductMessageCode.NOT_FOUND, productId);
    }
    return product;
  }

  private @NonNull Map<Long, Product> findActiveProducts(
      final @NonNull List<? extends OrderItemCreationRequest> items) {
    final Map<Long, Product> products = new LinkedHashMap<>();
    items.forEach(item -> products.computeIfAbsent(item.getProductId(), this::findActiveProduct));
    return products;
  }

  /** BR-V01–BR-V04: delegates voucher validation to the voucher domain when a code is supplied. */
  private @Nullable VoucherDTO validateVoucherIfPresent(
      final @Nullable String voucherCode, final BigDecimal orderAmount) {
    if (!VoucherCodeUtil.isProvided(voucherCode)) {
      return null;
    }
    return voucherValidationService.validate(Objects.requireNonNull(voucherCode), orderAmount);
  }

  /** BR-O03: the subtotal is always computed server-side from current product prices. */
  private static BigDecimal calculateSubtotal(
      final @NonNull List<? extends OrderItemCreationRequest> items,
      final Map<Long, ? extends Product> products) {
    return items.stream()
        .map(
            item ->
                products
                    .get(item.getProductId())
                    .getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private static @Nullable VoucherEntity toVoucherReference(final @Nullable VoucherDTO voucher) {
    if (null == voucher) {
      return null;
    }
    return new VoucherEntity().setId(voucher.getId());
  }
}
