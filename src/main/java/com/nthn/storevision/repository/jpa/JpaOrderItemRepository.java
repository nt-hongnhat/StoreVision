/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaOrderItemRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:31:21
 */

package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for {@link OrderItemEntity}.
 * Provides standard JPA operations for OrderItem entities.
 */
@Repository
public interface JpaOrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}
