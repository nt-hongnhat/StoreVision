/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaCustomerRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:40:54
 */

package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.CustomerEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for {@link CustomerEntity}. Provides standard JPA operations and custom query
 * methods for Customer entities.
 */
@Repository
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
  /**
   * Finds a customer by their email address.
   *
   * @param email The email address of the customer.
   * @return An {@link Optional} containing the {@link CustomerEntity} if found, or empty if not.
   */
  Optional<CustomerEntity> findByEmail(String email);
}
