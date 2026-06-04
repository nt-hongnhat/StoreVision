/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04
 */

package com.nthn.storevision.service.orderitem.impl;

import com.nthn.storevision.dto.OrderItemDTO;
import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.OrderItemMapper;
import com.nthn.storevision.repository.OrderItemRepository;
import com.nthn.storevision.service.orderitem.OrderItemUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link OrderItemUpdateService} for persisting updates to
 * {@link OrderItemEntity} instances in the database.
 */
@Service
@RequiredArgsConstructor
public class OrderItemUpdateServiceImpl implements OrderItemUpdateService {

  private final OrderItemRepository repository;
  private final OrderItemMapper mapper;

  @Transactional
  @Override
  public OrderItemDTO update(final Long id, final OrderItemDTO dto) {
    final OrderItemEntity entity = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("orderitem.not.found", id));
    mapper.updateEntity(dto, entity);
    repository.save(entity);
    return mapper.toDTO(entity);
  }
}
