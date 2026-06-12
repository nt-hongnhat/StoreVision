/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 17:15:08
 */

package com.nthn.storevision.service.order.impl;

import com.nthn.storevision.constant.OrderStatus;
import com.nthn.storevision.constant.message.CustomerMessageCode;
import com.nthn.storevision.constant.message.ProductMessageCode;
import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.dto.OrderItemDTO;
import com.nthn.storevision.dto.request.OrderCreationRequest;
import com.nthn.storevision.dto.request.OrderItemCreationRequest;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.exception.InsufficientStockException;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.customer.CustomerRetrievalService;
import com.nthn.storevision.service.order.OrderCreationService;
import com.nthn.storevision.service.orderitem.OrderItemCreationService;
import com.nthn.storevision.service.product.ProductRetrievalService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link OrderCreationService} that extends {@link AbstractCreatable} to
 * persist {@link OrderEntity} instances via {@link OrderRepository}.
 *
 * <p>{@link #createOrder} implements the sales-order creation flow: input validation (customer and
 * products must exist and be active), stock availability check without deduction (BR-O01),
 * server-side total calculation (BR-O03), unit-price lock at creation time (BR-O04). The order and
 * all its items are persisted in a single transaction with status {@link OrderStatus#PENDING}.
 */
@Slf4j
@Service
public class OrderCreationServiceImpl
    extends AbstractCreatable<OrderDTO, OrderEntity, OrderRepository>
    implements OrderCreationService {

  private final CustomerRetrievalService customerRetrievalService;
  private final OrderItemCreationService orderItemCreationService;
  private final ProductRetrievalService productRetrievalService;

  protected OrderCreationServiceImpl(
      final EntityMapperConfig<OrderDTO, ?, OrderEntity> mapper,
      final OrderRepository repository,
      final CustomerRetrievalService customerRetrievalService,
      final OrderItemCreationService orderItemCreationService,
      final ProductRetrievalService productRetrievalService) {
    super(mapper, repository);
    this.customerRetrievalService = customerRetrievalService;
    this.orderItemCreationService = orderItemCreationService;
    this.productRetrievalService = productRetrievalService;
  }

  /** {@inheritDoc} */
  @Override
  @Transactional
  public OrderDTO createOrder(final @NonNull OrderCreationRequest request) {
    log.debug(
        "Creating order: customerId={}, items={}",
        request.getCustomerId(),
        request.getItems().size());

    final CustomerEntity customer = findActiveCustomer(request.getCustomerId());
    final Map<Long, ProductEntity> products = findActiveProducts(request.getItems());

    verifyStockAvailability(request.getItems(), products);

    final BigDecimal totalAmount = calculateTotalAmount(request.getItems(), products);

    final OrderEntity saved =
        getRepository()
            .save(
                OrderEntity.builder()
                    .customer(customer)
                    .orderDate(LocalDateTime.now())
                    .status(OrderStatus.PENDING)
                    .totalAmount(totalAmount)
                    .build());

    final List<OrderItemDTO> items = createOrderItems(saved.getId(), request.getItems(), products);

    final OrderDTO result = getMapper().toDTO(saved);
    result.setOrderItems(items);

    log.info(
        "Order created: id={}, customerId={}, items={}, totalAmount={}",
        saved.getId(),
        customer.getId(),
        items.size(),
        totalAmount);
    return result;
  }

  private CustomerEntity findActiveCustomer(final Long customerId) {
    final CustomerEntity customer =
        customerRetrievalService.findById(customerId, Function.identity());
    if (customer.getDeletedDate().isPresent()) {
      throw new EntityNotFoundException(CustomerMessageCode.NOT_FOUND, customerId);
    }
    return customer;
  }

  private Map<Long, ProductEntity> findActiveProducts(
      final List<OrderItemCreationRequest> items) {
    final Map<Long, ProductEntity> products = new LinkedHashMap<>();
    items.forEach(item -> products.computeIfAbsent(item.getProductId(), this::findActiveProduct));
    return products;
  }

  private ProductEntity findActiveProduct(final Long productId) {
    final ProductEntity product = productRetrievalService.findById(productId, Function.identity());
    if (product.getDeletedDate().isPresent()) {
      throw new EntityNotFoundException(ProductMessageCode.NOT_FOUND, productId);
    }
    return product;
  }

  /**
   * BR-O01 at creation time: verifies stock is sufficient without deducting it — deduction only
   * happens when the order is completed. Quantities of duplicate products are summed so the check
   * covers the whole order, not each line in isolation.
   */
  private void verifyStockAvailability(
      final List<OrderItemCreationRequest> items, final Map<Long, ProductEntity> products) {
    final Map<Long, Integer> requestedPerProduct = new LinkedHashMap<>();
    items.forEach(
        item -> requestedPerProduct.merge(item.getProductId(), item.getQuantity(), Integer::sum));

    requestedPerProduct.forEach(
        (productId, requested) -> {
          final ProductEntity product = products.get(productId);
          if (product.getStockQuantity() < requested) {
            throw new InsufficientStockException(
                product.getName(), product.getStockQuantity(), requested);
          }
        });
  }

  /** BR-O03: the total amount is always computed server-side from current product prices. */
  private BigDecimal calculateTotalAmount(
      final List<OrderItemCreationRequest> items, final Map<Long, ProductEntity> products) {
    return items.stream()
        .map(
            item ->
                products
                    .get(item.getProductId())
                    .getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  /** BR-O04: each item's unit price is locked from the product's price at creation time. */
  private List<OrderItemDTO> createOrderItems(
      final Long orderId,
      final List<OrderItemCreationRequest> items,
      final Map<Long, ProductEntity> products) {
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
}
