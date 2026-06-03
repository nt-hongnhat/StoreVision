package com.nthn.storevision.controller;

import com.nthn.storevision.dto.EmployeeDTO;
import jakarta.validation.Valid;
import java.util.List;

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
   * Retrieves all active employees.
   *
   * @return list of all non-deleted employees
   */
  List<EmployeeDTO> getAllEmployees();

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
   * @param id  the ID of the employee to update
   * @param dto the updated employee data
   * @return the updated employee
   */
  EmployeeDTO updateEmployee(Long id, @Valid EmployeeDTO dto);
}
