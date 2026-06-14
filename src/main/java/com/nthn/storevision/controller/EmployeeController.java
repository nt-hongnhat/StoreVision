/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeController.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 11:44:10
 */

package com.nthn.storevision.controller;

import com.nthn.storevision.dto.EmployeeDTO;
import com.nthn.storevision.dto.response.PageDTO;
import jakarta.validation.Valid;

/**
 * API contract for employee management endpoints.
 *
 * @see com.nthn.storevision.controller.v1.EmployeeControllerV1
 */
public interface EmployeeController {

  /**
   * Creates a new employee.
   *
   * @param dto the employee data to persist
   * @return the created employee
   */
  EmployeeDTO createEmployee(@Valid EmployeeDTO dto);

  /**
   * Soft-deletes an employee by ID.
   *
   * @param id the ID of the employee to delete
   */
  void deleteEmployee(Long id);

  /**
   * Retrieves one page of active employees.
   *
   * @param page zero-based page index; negative values are treated as 0
   * @param size requested page size; clamped to the range [1, 100]
   * @return the requested page of employees
   */
  PageDTO<EmployeeDTO> getAllEmployees(int page, int size);

  /**
   * Retrieves an employee by ID.
   *
   * @param id the ID of the employee to retrieve
   * @return the matching employee
   */
  EmployeeDTO getEmployeeById(Long id);

  /**
   * Updates an existing employee.
   *
   * @param id the ID of the employee to update
   * @param dto the updated employee data
   * @return the updated employee
   */
  EmployeeDTO updateEmployee(Long id, @Valid EmployeeDTO dto);
}
