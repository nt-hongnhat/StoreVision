/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:39:48
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.OrderItemEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link OrderItemEntity} instances.
 * Defines standard CRUD operations for order items.
 */
public interface OrderItemRepository extends ReadRepository<OrderItemEntity, Long> {
  /**
   * Deletes an order item by its ID.
   *
   * @param id The ID of the order item to delete.
   */
  void deleteById(Long id);

  /**
   * Checks if an order item with the given ID exists.
   *
   * @param id The ID of the order item to check.
   * @return {@code true} if an order item with the given ID exists, {@code false} otherwise.
   */
  boolean existsById(Long id);

  /**
   * Retrieves all order items.
   *
   * @return A list of all {@link OrderItemEntity} instances.
   */
  List<OrderItemEntity> findAll();

  /**
   * Retrieves an order item by its ID.
   *
   * @param id The ID of the order item to retrieve.
   * @return An {@link Optional} containing the {@link OrderItemEntity} if found, or empty if not.
   */
  Optional<OrderItemEntity> findById(Long id);

  /**
   * Saves an order item entity.
   *
   * @param orderItem The {@link OrderItemEntity} to save.
   * @return The saved {@link OrderItemEntity}.
   */
  OrderItemEntity save(OrderItemEntity orderItem);
}
