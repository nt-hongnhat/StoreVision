/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaOrderRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:15:54
 */

package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.constant.OrderStatus;
import com.nthn.storevision.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for {@link OrderEntity}. Provides standard and order-specific queries.
 */
@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {

  /**
   * BR-P04: returns {@code true} if any non-deleted order in the given status contains the
   * specified product. Used by the product soft-delete flow to emit a warning before deletion.
   */
  @Query(
      "SELECT (COUNT(o) > 0) FROM OrderEntity o JOIN o.orderItems oi"
          + " WHERE oi.product.id = :productId AND o.status = :status AND o.deletedDate IS NULL")
  boolean existsByOrderItemsProductIdAndStatus(
      @Param("productId") Long productId, @Param("status") OrderStatus status);
}
