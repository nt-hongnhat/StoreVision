/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:12:00
 */

package com.nthn.storevision.service.orderitem.impl;

import com.nthn.storevision.dto.OrderItemDTO;
import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.mapper.EntityMapper;
import com.nthn.storevision.mapper.OrderItemMapper;
import com.nthn.storevision.repository.OrderItemRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.orderitem.OrderItemCreationService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link OrderItemCreationService} that extends {@link AbstractCreatable}
 * to persist {@link OrderItemEntity} instances via {@link OrderItemRepository}.
 */
@Service
public class OrderItemCreationServiceImpl
    extends AbstractCreatable<OrderItemDTO, OrderItemEntity, OrderItemRepository>
    implements OrderItemCreationService {

  protected OrderItemCreationServiceImpl(
      final EntityMapper<OrderItemDTO, ?, OrderItemEntity> mapper, final OrderItemRepository repository) {
    super(mapper, repository);
  }
}
