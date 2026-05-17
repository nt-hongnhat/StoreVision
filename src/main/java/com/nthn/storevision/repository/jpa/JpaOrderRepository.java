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

import com.nthn.storevision.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for {@link OrderEntity}.
 * Provides standard JPA operations for Order entities.
 */
@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {
}
