/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemRepositoryImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:40:44
 */

package com.nthn.storevision.repository.impl;

import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.repository.OrderItemRepository;
import com.nthn.storevision.repository.jpa.JpaOrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link OrderItemRepository} interface.
 * This class acts as an adapter, delegating calls to the underlying {@link JpaOrderItemRepository}.
 */
@Repository
@RequiredArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository {

  private final JpaOrderItemRepository jpaRepository;

  @Override
  public void deleteById(final Long id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public boolean existsById(final Long id) {
    return jpaRepository.existsById(id);
  }

  @Override
  public List<OrderItemEntity> findAll() {
    return jpaRepository.findAll();
  }

  @Override
  public Optional<OrderItemEntity> findById(final Long id) {
    return jpaRepository.findById(id);
  }

  @Override
  public OrderItemEntity save(final OrderItemEntity orderItem) {
    return jpaRepository.save(orderItem);
  }
}
