/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaOrderItemRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:07:00
 */

package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.OrderItemEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
