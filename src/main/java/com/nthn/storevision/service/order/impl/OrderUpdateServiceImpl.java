/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 16:32:19
 */

package com.nthn.storevision.service.order.impl;

import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.EntityMapper;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.common.AbstractUpdatable;
import com.nthn.storevision.service.order.OrderUpdateService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link OrderUpdateService} for persisting updates to {@link
 * OrderEntity} instances in the database.
 */
@Service
public class OrderUpdateServiceImpl
    extends AbstractUpdatable<OrderDTO, Long, OrderEntity, OrderRepository>
    implements OrderUpdateService {

  protected OrderUpdateServiceImpl(
      final EntityMapper<OrderDTO, Long, OrderEntity> mapper,
      final OrderRepository repository) {
    super(mapper, repository);
  }

  @Override
  protected OrderEntity getById(final Long id) {
    return getRepository()
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("order.not.found", id));
  }
}
