/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:42:28
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.model.Customer;
import com.nthn.storevision.repository.generic.DeleteRepository;
import com.nthn.storevision.repository.generic.ReadRepository;
import com.nthn.storevision.repository.generic.SaveRepository;
import java.util.Optional;

/**
 * Repository interface for managing {@link CustomerEntity} instances. Defines standard CRUD
 * operations and custom queries for customers.
 */
public interface CustomerRepository
    extends ReadRepository<CustomerEntity, Long>, SaveRepository<CustomerEntity>, DeleteRepository<Long> {

  /**
   * Retrieves a customer by their email address.
   *
   * @param email the email address to search for.
   * @return an {@link Optional} containing the {@link CustomerEntity} if found, or an empty {@link
   *     Optional} if not found.
   */
  Optional<CustomerEntity> findByEmail(String email);
}
