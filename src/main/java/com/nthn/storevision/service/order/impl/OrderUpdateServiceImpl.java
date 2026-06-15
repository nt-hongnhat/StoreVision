/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: OrderUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:51:59
 */

package com.nthn.storevision.service.order.impl;

import com.nthn.storevision.constant.order.OrderStatus;
import com.nthn.storevision.constant.message.OrderMessageCode;
import com.nthn.storevision.dto.order.OrderDTO;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.exception.InsufficientStockException;
import com.nthn.storevision.exception.order.InvalidOrderStateException;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.common.AbstractUpdatable;
import com.nthn.storevision.service.customer.LoyaltyService;
import com.nthn.storevision.service.inventory.InventoryCreationService;
import com.nthn.storevision.service.order.OrderUpdateService;
import com.nthn.storevision.service.product.ProductUpdateService;
import com.nthn.storevision.service.voucher.VoucherUpdateService;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link OrderUpdateService} for persisting updates to {@link
 * OrderEntity} instances in the database.
 *
 * <p>{@link #complete} implements the payment confirmation flow ({@code PENDING → COMPLETED}):
 * stock double-check (BR-O01), stock deduction with {@code ISSUE} movements in the same
 * transaction (BR-I05), loyalty point award and voucher usage increment. {@link #cancel}
 * implements {@code PENDING → CANCELLED} without touching stock or points (BR-O02).
 */
@Slf4j
@Service
public class OrderUpdateServiceImpl
    extends AbstractUpdatable<OrderDTO, Long, OrderEntity, OrderRepository>
    implements OrderUpdateService {

  private final InventoryCreationService inventoryCreationService;
  private final LoyaltyService loyaltyService;
  private final ProductUpdateService productUpdateService;
  private final VoucherUpdateService voucherUpdateService;

  protected OrderUpdateServiceImpl(
      final EntityMapperConfig<OrderDTO, Long, OrderEntity> mapper,
      final OrderRepository repository,
      final InventoryCreationService inventoryCreationService,
      final LoyaltyService loyaltyService,
      final ProductUpdateService productUpdateService,
      final VoucherUpdateService voucherUpdateService) {
    super(mapper, repository);
    this.inventoryCreationService = inventoryCreationService;
    this.loyaltyService = loyaltyService;
    this.productUpdateService = productUpdateService;
    this.voucherUpdateService = voucherUpdateService;
  }

  /** {@inheritDoc} */
  @Override
  @Transactional
  public OrderDTO cancel(final @NonNull Long orderId) {
    log.debug("Cancelling order id: {}", orderId);
    final OrderEntity order = findPendingOrderOrThrow(orderId);

    order.setStatus(OrderStatus.CANCELLED);
    getRepository().save(order);

    log.info("Order cancelled: id={}", orderId);
    return getMapper().toDTO(order);
  }

  /** {@inheritDoc} */
  @Override
  @Transactional
  public OrderDTO complete(final @NonNull Long orderId) {
    log.debug("Completing order id: {}", orderId);
    final OrderEntity order = findPendingOrderOrThrow(orderId);
    final List<OrderItemEntity> items = order.getOrderItems();

    verifyStockAvailability(items);

    items.forEach(
        item -> {
          final Long productId = item.getProduct().getId();
          productUpdateService.deductStock(productId, item.getQuantity());
          inventoryCreationService.recordIssue(productId, item.getQuantity(), orderId);
        });

    order.setStatus(OrderStatus.COMPLETED);
    getRepository().save(order);

    loyaltyService.addPoints(order.getCustomer().getId(), order.getTotalAmount(), orderId);

    if (null != order.getVoucher()) {
      voucherUpdateService.incrementUsage(order.getVoucher().getId());
    }

    log.info("Order completed: id={}, totalAmount={}", orderId, order.getTotalAmount());
    return getMapper().toDTO(order);
  }

  @Override
  protected OrderEntity getById(final Long id) {
    return getRepository()
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(OrderMessageCode.NOT_FOUND, id));
  }

  /**
   * Loads the order and asserts it is an active {@code PENDING} order. Soft-deleted orders are
   * treated as not found; non-PENDING orders are final states and reject any transition (BR-O02).
   */
  private OrderEntity findPendingOrderOrThrow(final Long orderId) {
    final OrderEntity order = getById(orderId);
    if (order.getDeletedDate().isPresent()) {
      throw new EntityNotFoundException(OrderMessageCode.NOT_FOUND, orderId);
    }
    if (OrderStatus.PENDING != order.getStatus()) {
      throw new InvalidOrderStateException(orderId, order.getStatus());
    }
    return order;
  }

  /**
   * BR-O01 at completion time: double-checks stock to guard against changes between order creation
   * and payment confirmation. Quantities of duplicate products are summed so the check covers the
   * whole order.
   */
  private void verifyStockAvailability(final List<OrderItemEntity> items) {
    final Map<Long, ProductEntity> products = new LinkedHashMap<>();
    final Map<Long, Integer> requestedPerProduct = new LinkedHashMap<>();
    items.forEach(
        item -> {
          products.putIfAbsent(item.getProduct().getId(), item.getProduct());
          requestedPerProduct.merge(item.getProduct().getId(), item.getQuantity(), Integer::sum);
        });

    requestedPerProduct.forEach(
        (productId, requested) -> {
          final ProductEntity product = products.get(productId);
          if (product.getStockQuantity() < requested) {
            throw new InsufficientStockException(
                product.getName(), product.getStockQuantity(), requested);
          }
        });
  }
}
