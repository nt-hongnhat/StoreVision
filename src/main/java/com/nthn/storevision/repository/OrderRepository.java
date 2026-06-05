/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 15:40:26
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.repository.generic.DeleteRepository;
import com.nthn.storevision.repository.generic.ReadRepository;
import com.nthn.storevision.repository.generic.SaveRepository;

/**
 * Repository interface for managing {@link OrderEntity} instances. Defines standard CRUD operations
 * for orders.
 */
public interface OrderRepository
    extends ReadRepository<OrderEntity, Long>, SaveRepository<OrderEntity>, DeleteRepository<Long> {

  /**
   * Checks if there are any pending orders for the specified product.
   *
   * @param productId the ID of the product to check for pending orders.
   * @return {@code true} if there is at least one pending order for the given product; {@code
   *     false} otherwise.
   */
  boolean existsPendingOrderForProduct(Long productId);
}
