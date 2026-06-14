/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaOrderItemRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 12:05:55
 */

package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.OrderItemEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for {@link OrderItemEntity}. Provides standard JPA operations for OrderItem
 * entities.
 */
@Repository
public interface JpaOrderItemRepository extends JpaRepository<OrderItemEntity, Long> {

  /**
   * Returns all active (non-soft-deleted) order items.
   *
   * @return the list of active order items; never {@code null}
   */
  List<OrderItemEntity> findByDeletedDateIsNull();

  /**
   * Returns one page of active (non-soft-deleted) order items.
   *
   * @param pageable page request carrying index, size, and sort; must not be {@code null}
   * @return the requested page of active order items; never {@code null}
   */
  Page<OrderItemEntity> findByDeletedDateIsNull(Pageable pageable);

  /**
   * Returns one page of active order items, optionally filtered by their parent order. The filter
   * is optional: passing {@code null} for {@code orderId} skips it entirely.
   *
   * @param orderId the parent order ID to filter by; may be {@code null} to skip the filter
   * @param pageable page request carrying index, size, and sort; must not be {@code null}
   * @return the requested page of active order items matching the filter; never {@code null}
   */
  @Query(
      """
          SELECT oi FROM OrderItemEntity oi WHERE oi.deletedDate IS NULL
           AND (:orderId IS NULL OR oi.order.id = :orderId)""")
  Page<OrderItemEntity> searchByOrderId(@Param("orderId") Long orderId, Pageable pageable);
}
