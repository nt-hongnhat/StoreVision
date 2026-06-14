/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 12:05:36
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.repository.generic.DeleteRepository;
import com.nthn.storevision.repository.generic.ReadRepository;
import com.nthn.storevision.repository.generic.SaveRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Repository interface for managing {@link OrderItemEntity} instances. Defines standard CRUD
 * operations for order items.
 */
public interface OrderItemRepository
    extends ReadRepository<OrderItemEntity, Long>,
        SaveRepository<OrderItemEntity>,
        DeleteRepository<Long> {

  /**
   * Returns one page of active order items, optionally filtered by their parent order. The filter
   * is optional: passing {@code null} for {@code orderId} skips it entirely.
   *
   * @param orderId the parent order ID to filter by; may be {@code null} to skip the filter
   * @param pageable page request carrying index, size, and sort; must not be {@code null}
   * @return the requested page of active order items matching the filter; never {@code null}
   */
  Page<OrderItemEntity> searchByOrderId(Long orderId, Pageable pageable);
}
