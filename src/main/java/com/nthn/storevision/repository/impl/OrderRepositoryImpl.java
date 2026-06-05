/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderRepositoryImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 17:20:50
 */

package com.nthn.storevision.repository.impl;

import com.nthn.storevision.constant.OrderStatus;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.repository.jpa.JpaOrderRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the {@link OrderRepository} interface.
 * This class acts as an adapter, delegating calls to the underlying {@link JpaOrderRepository}.
 */
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

  private final JpaOrderRepository jpaRepository;

  @Override
  public void deleteById(final Long id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public void markDeletedById(final Long id) {
    findById(id).ifPresent(entity -> {
      entity.setDeleted(true);
      save(entity);
    });
  }

  @Override
  public void restoreById(final Long id) {
    findById(id).ifPresent(entity -> {
      entity.setDeletedBy(null);
      entity.setDeletedDate(null);
      entity.setDeleted(false);
      save(entity);
    });
  }

  @Override
  public boolean existsPendingOrderForProduct(final Long productId) {
    return jpaRepository.existsByOrderItemsProductIdAndStatus(productId, OrderStatus.PENDING);
  }

  @Override
  public List<OrderEntity> findAll() {
    return jpaRepository.findAll();
  }

  @Override
  public Optional<OrderEntity> findById(final Long id) {
    return jpaRepository.findById(id);
  }

  @Override
  public boolean isExistsById(final Long id) {
    return jpaRepository.existsById(id);
  }

  @Override
  public OrderEntity save(final OrderEntity order) {
    return jpaRepository.save(order);
  }
}
