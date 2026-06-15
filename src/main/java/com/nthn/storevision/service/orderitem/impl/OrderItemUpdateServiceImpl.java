/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 17:15:08
 */

package com.nthn.storevision.service.orderitem.impl;

import com.nthn.storevision.constant.message.OrderItemMessageCode;
import com.nthn.storevision.dto.order_item.OrderItemDTO;
import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.repository.OrderItemRepository;
import com.nthn.storevision.service.common.AbstractUpdatable;
import com.nthn.storevision.service.orderitem.OrderItemUpdateService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link OrderItemUpdateService} for persisting updates to {@link
 * OrderItemEntity} instances in the database.
 */
@Service
public class OrderItemUpdateServiceImpl
    extends AbstractUpdatable<OrderItemDTO, Long, OrderItemEntity, OrderItemRepository>
    implements OrderItemUpdateService {

  protected OrderItemUpdateServiceImpl(
      final EntityMapperConfig<OrderItemDTO, Long, OrderItemEntity> mapper,
      final OrderItemRepository repository) {
    super(mapper, repository);
  }

  @Override
  protected OrderItemEntity getById(final Long id) {
    return getRepository()
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(OrderItemMessageCode.NOT_FOUND, id));
  }
}
