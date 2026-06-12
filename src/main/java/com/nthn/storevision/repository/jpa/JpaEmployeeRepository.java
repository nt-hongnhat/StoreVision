/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaEmployeeRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:07:04
 */

package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.EmployeeEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for {@link EmployeeEntity}. Provides standard JPA operations and custom query
 * methods for Employee entities.
 */
@Repository
public interface JpaEmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
  /**
   * Returns all active (non-soft-deleted) employees.
   *
   * @return the list of active employees; never {@code null}
   */
  List<EmployeeEntity> findByDeletedDateIsNull();

  /**
   * Finds an employee by their username.
   *
   * @param username The username of the employee.
   * @return An {@link Optional} containing the {@link EmployeeEntity} if found, or empty if not.
   */
  Optional<EmployeeEntity> findByUsername(String username);
}
