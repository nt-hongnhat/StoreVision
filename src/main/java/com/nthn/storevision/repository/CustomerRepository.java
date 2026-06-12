/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:03:04
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.repository.generic.DeleteRepository;
import com.nthn.storevision.repository.generic.ReadRepository;
import com.nthn.storevision.repository.generic.SaveRepository;
import java.util.Optional;

/**
 * Repository interface for managing {@link CustomerEntity} instances. Defines standard CRUD
 * operations and custom queries for customers.
 */
public interface CustomerRepository
    extends ReadRepository<CustomerEntity, Long>,
        SaveRepository<CustomerEntity>,
        DeleteRepository<Long> {

  /**
   * Retrieves a customer by their email address.
   *
   * @param email the email address to search for.
   * @return an {@link Optional} containing the {@link CustomerEntity} if found, or an empty {@link
   *     Optional} if not found.
   */
  Optional<CustomerEntity> findByEmail(String email);

  /**
   * BR-C01: checks whether the given email is already registered to any customer, including
   * soft-deleted records.
   *
   * @param email the email address to check; must not be {@code null}
   * @return {@code true} if the email is already taken
   */
  boolean isEmailTaken(String email);

  /**
   * BR-C01 on update: checks whether the given email is registered to any customer other than the
   * specified one, including soft-deleted records.
   *
   * @param email the email address to check; must not be {@code null}
   * @param excludedId the customer being updated, excluded from the check; must not be {@code null}
   * @return {@code true} if the email is taken by another customer
   */
  boolean isEmailTakenByOther(String email, Long excludedId);
}
