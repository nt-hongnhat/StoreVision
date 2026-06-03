/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 11:21:37
 */

package com.nthn.storevision.service.order.impl;

import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.order.OrderUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link OrderUpdateService} for persisting updates to {@link
 * OrderEntity} instances in the database.
 */
@Service
@RequiredArgsConstructor
public class OrderUpdateServiceImpl implements OrderUpdateService {

  private final OrderRepository repository;

  @Transactional
  @Override
  public void update(final OrderEntity order) {
    repository.save(order);
  }
}
