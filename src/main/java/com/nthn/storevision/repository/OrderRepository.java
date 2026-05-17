/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:36:00
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.OrderEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link OrderEntity} instances.
 * Defines standard CRUD operations for orders.
 */
public interface OrderRepository {
  /**
   * Deletes an order by its ID.
   *
   * @param id The ID of the order to delete.
   */
  void deleteById(Long id);

  /**
   * Checks if an order with the given ID exists.
   *
   * @param id The ID of the order to check.
   * @return {@code true} if an order with the given ID exists, {@code false} otherwise.
   */
  boolean existsById(Long id);

  /**
   * Retrieves all orders.
   *
   * @return A list of all {@link OrderEntity} instances.
   */
  List<OrderEntity> findAll();

  /**
   * Retrieves an order by its ID.
   *
   * @param id The ID of the order to retrieve.
   * @return An {@link Optional} containing the {@link OrderEntity} if found, or empty if not.
   */
  Optional<OrderEntity> findById(Long id);

  /**
   * Saves an order entity.
   *
   * @param order The {@link OrderEntity} to save.
   * @return The saved {@link OrderEntity}.
   */
  OrderEntity save(OrderEntity order);
}
