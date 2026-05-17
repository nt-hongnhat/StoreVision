/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:52:42
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.EmployeeEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link EmployeeEntity} instances.
 * Defines standard CRUD operations and custom queries for employees.
 */
public interface EmployeeRepository {
  /**
   * Deletes an employee by its ID.
   *
   * @param id The ID of the employee to delete.
   */
  void deleteById(Long id);

  /**
   * Checks if an employee with the given ID exists.
   *
   * @param id The ID of the employee to check.
   * @return {@code true} if an employee with the given ID exists, {@code false} otherwise.
   */
  boolean existsById(Long id);

  /**
   * Retrieves all employees.
   *
   * @return A list of all {@link EmployeeEntity} instances.
   */
  List<EmployeeEntity> findAll();

  /**
   * Retrieves an employee by its ID.
   *
   * @param id The ID of the employee to retrieve.
   * @return An {@link Optional} containing the {@link EmployeeEntity} if found, or empty if not.
   */
  Optional<EmployeeEntity> findById(Long id);

  /**
   * Saves an employee entity.
   *
   * @param employee The {@link EmployeeEntity} to save.
   * @return The saved {@link EmployeeEntity}.
   */
  EmployeeEntity save(EmployeeEntity employee);

  /**
   * Finds an employee by their username.
   *
   * @param username The username of the employee.
   * @return An {@link Optional} containing the {@link EmployeeEntity} if found, or empty if not.
   */
  Optional<EmployeeEntity> findByUsername(String username);
}
