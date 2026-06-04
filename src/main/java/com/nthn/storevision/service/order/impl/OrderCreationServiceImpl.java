/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 11:21:37
 */

package com.nthn.storevision.service.order.impl;

import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.mapper.OrderMapper;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.order.OrderCreationService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link OrderCreationService} that extends {@link AbstractCreatable} to
 * persist {@link OrderEntity} instances via {@link OrderRepository}.
 */
@Service
public class OrderCreationServiceImpl
    extends AbstractCreatable<OrderDTO, OrderEntity>
    implements OrderCreationService {

  public OrderCreationServiceImpl(final OrderRepository repository, final OrderMapper mapper) {
    super(repository::save, mapper::toDTO, mapper::toEntity);
  }
}
