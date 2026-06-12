/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:03:04
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.repository.generic.DeleteRepository;
import com.nthn.storevision.repository.generic.ReadRepository;
import com.nthn.storevision.repository.generic.SaveRepository;
import java.util.List;

/**
 * Repository interface for managing {@link OrderEntity} instances. Defines standard CRUD operations
 * for orders.
 */
public interface OrderRepository
    extends ReadRepository<OrderEntity, Long>, SaveRepository<OrderEntity>, DeleteRepository<Long> {

  /**
   * BR-C02: checks if the specified customer still has any pending orders.
   *
   * @param customerId the ID of the customer to check; must not be {@code null}
   * @return {@code true} if there is at least one active pending order for the given customer
   */
  boolean existsPendingOrderForCustomer(Long customerId);

  /**
   * Checks if there are any pending orders for the specified product.
   *
   * @param productId the ID of the product to check for pending orders.
   * @return {@code true} if there is at least one pending order for the given product; {@code
   *     false} otherwise.
   */
  boolean existsPendingOrderForProduct(Long productId);

  /**
   * Returns all active (non-deleted) orders of a customer, ordered by most recent first.
   *
   * @param customerId the ID of the customer; must not be {@code null}
   * @return list of the customer's orders; never {@code null}
   */
  List<OrderEntity> findAllByCustomerId(Long customerId);
}
