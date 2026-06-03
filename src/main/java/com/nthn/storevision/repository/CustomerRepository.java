/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:31:36
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link CustomerEntity} instances.
 * Defines standard CRUD operations and custom queries for customers.
 */
public interface CustomerRepository extends ReadRepository<CustomerEntity, Long> {
  /**
   * Deletes a customer by its ID.
   *
   * @param id The ID of the customer to delete.
   */
  void deleteById(Long id);

  /**
   * Checks if a customer with the given ID exists.
   *
   * @param id The ID of the customer to check.
   * @return {@code true} if a customer with the given ID exists, {@code false} otherwise.
   */
  boolean existsById(Long id);

  /**
   * Retrieves all customers.
   *
   * @return A list of all {@link CustomerEntity} instances.
   */
  List<CustomerEntity> findAll();

  /**
   * Retrieves a customer by its ID.
   *
   * @param id The ID of the customer to retrieve.
   * @return An {@link Optional} containing the {@link CustomerEntity} if found, or empty if not.
   */
  Optional<CustomerEntity> findById(Long id);

  /**
   * Saves a customer entity.
   *
   * @param customer The {@link CustomerEntity} to save.
   * @return The saved {@link CustomerEntity}.
   */
  CustomerEntity save(CustomerEntity customer);

  /**
   * Finds a customer by their email address.
   *
   * @param email The email address of the customer.
   * @return An {@link Optional} containing the {@link CustomerEntity} if found, or empty if not.
   */
  Optional<CustomerEntity> findByEmail(String email);
}
