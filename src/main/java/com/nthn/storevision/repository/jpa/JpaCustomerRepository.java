/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaCustomerRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:02:30
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

  /**
   * BR-C01 on update: checks whether any customer other than the given one — including soft-deleted
   * records — carries the given email.
   *
   * @param email the email address to check; must not be {@code null}
   * @param id the customer being updated, excluded from the check; must not be {@code null}
   * @return {@code true} if the email is registered to another customer
   */
  @Query(
      "SELECT (COUNT(c) > 0) FROM CustomerEntity c"
          + " WHERE c.email = :email AND c.id <> :id")
  boolean isEmailAlreadyRegistered(@Param("email") String email, @Param("id") Long id);

  /**
   * BR-C01: checks whether any customer — including soft-deleted records — carries the given email.
   *
   * @param email the email address to check; must not be {@code null}
   * @return {@code true} if the email is already registered
   */
  @Query("SELECT (COUNT(c) > 0) FROM CustomerEntity c WHERE c.email = :email")
  boolean isEmailRegistered(@Param("email") String email);
}
