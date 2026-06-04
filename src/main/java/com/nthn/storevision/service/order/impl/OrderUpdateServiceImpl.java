/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04
 */

package com.nthn.storevision.service.order.impl;

import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.OrderMapper;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.order.OrderUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link OrderUpdateService} for persisting updates to
 * {@link OrderEntity} instances in the database.
 */
@Service
@RequiredArgsConstructor
public class OrderUpdateServiceImpl implements OrderUpdateService {

  private final OrderRepository repository;
  private final OrderMapper mapper;

  @Transactional
  @Override
  public OrderDTO update(final Long id, final OrderDTO dto) {
    final OrderEntity entity = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("order.not.found", id));
    mapper.updateEntity(dto, entity);
    repository.save(entity);
    return mapper.toDTO(entity);
  }
}
