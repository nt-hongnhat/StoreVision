/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:53:15
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.repository.generic.DeleteRepository;
import com.nthn.storevision.repository.generic.ReadRepository;
import com.nthn.storevision.repository.generic.SaveRepository;
import java.util.Optional;

/**
 * Repository interface for managing {@link EmployeeEntity} instances. Defines standard CRUD
 * operations and custom queries for employees.
 */
public interface EmployeeRepository
    extends ReadRepository<EmployeeEntity, Long>,
        SaveRepository<EmployeeEntity>,
        DeleteRepository<Long> {

  /**
   * Retrieves an employee by their username.
   *
   * @param username the username to search for.
   * @return an {@code Optional} containing the {@code EmployeeEntity} if found, or an empty {@code
   *     Optional} if not.
   */
  Optional<EmployeeEntity> findByUsername(String username);
}
