/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaOrderRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:06:51
 */

package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.constant.OrderStatus;
import com.nthn.storevision.entity.OrderEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** JPA Repository for {@link OrderEntity}. Provides standard and order-specific queries. */
@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {

  /**
   * BR-C02: returns {@code true} if any non-deleted order of the given customer is in the given
   * status. Used by the customer deletion flow to reject deleting customers with PENDING orders.
   *
   * @param customerId the customer ID to check; must not be {@code null}
   * @param status the order status to check; must not be {@code null}
   * @return {@code true} if any non-deleted order of the given customer is in the given status;
   *     {@code false} otherwise
   */
  @Query(
      "SELECT (COUNT(o) > 0) FROM OrderEntity o"
          + " WHERE o.customer.id = :customerId AND o.status = :status AND o.deletedDate IS NULL")
  boolean existsByCustomerIdAndStatus(
      @Param("customerId") Long customerId, @Param("status") OrderStatus status);

  /**
   * BR-P04: returns {@code true} if any non-deleted order in the given status contains the
   * specified product. Used by the product soft-delete flow to emit a warning before deletion.
   *
   * @param productId the product ID to check; must not be {@code null}
   * @param status the order status to check; must not be {@code null}
   * @return {@code true} if any non-deleted order in the given status contains the specified
   *     product; {@code false} otherwise
   */
  @Query(
      "SELECT (COUNT(o) > 0) FROM OrderEntity o JOIN o.orderItems oi"
          + " WHERE oi.product.id = :productId AND o.status = :status AND o.deletedDate IS NULL")
  boolean existsByOrderItemsProductIdAndStatus(
      @Param("productId") Long productId, @Param("status") OrderStatus status);

  /**
   * Returns all non-deleted orders of a customer, ordered by the most recent first. Backs the
   * customer order-history endpoint.
   *
   * @param customerId the customer ID to query; must not be {@code null}
   * @return the list of orders; never {@code null}
   */
  List<OrderEntity> findByCustomerIdAndDeletedDateIsNullOrderByOrderDateDesc(Long customerId);

  /**
   * Returns all active (non-soft-deleted) orders.
   *
   * @return the list of active orders; never {@code null}
   */
  List<OrderEntity> findByDeletedDateIsNull();
}
