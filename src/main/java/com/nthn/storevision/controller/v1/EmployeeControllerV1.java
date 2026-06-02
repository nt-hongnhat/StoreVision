/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:30:06
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.dto.EmployeeDTO;
import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.mapper.EmployeeMapper;
import com.nthn.storevision.service.employee.EmployeeCreationService;
import com.nthn.storevision.service.employee.EmployeeDeletionService;
import com.nthn.storevision.service.employee.EmployeeRetrievalService;
import com.nthn.storevision.service.employee.EmployeeUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing employees.
 * Provides endpoints for CRUD operations on employees.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeControllerV1 {

  private final EmployeeCreationService employeeCreationService;
  private final EmployeeRetrievalService employeeRetrievalService;
  private final EmployeeUpdateService employeeUpdateService;
  private final EmployeeDeletionService employeeDeletionService;
  private final EmployeeMapper employeeMapper;

  /**
   * Retrieves a list of all employees.
   *
   * @return A ResponseEntity containing a list of EmployeeDTOs.
   */
  @GetMapping
  public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
    log.debug("GET /employees");
    return ResponseEntity.ok(employeeRetrievalService.findAllEmployeesAsDTO());
  }

  /**
   * Retrieves an employee by its unique identifier.
   *
   * @param id The ID of the employee to retrieve.
   * @return A ResponseEntity containing the EmployeeDTO if found, or a not found status.
   */
  @GetMapping("/{id}")
  public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable final Long id) {
    log.debug("GET /employees/{}", id);
    return ResponseEntity.ok(employeeRetrievalService.findEmployeeDTOById(id));
  }

  /**
   * Creates a new employee.
   *
   * @param employeeDTO The EmployeeDTO containing the details of the employee to create.
   * @return A ResponseEntity containing the created EmployeeDTO.
   */
  @PostMapping
  public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody final EmployeeDTO employeeDTO) {
    log.debug("POST /employees");
    EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeDTO);
    // In a real application, you would hash the password before saving
    employeeCreationService.create(employeeEntity);
    return new ResponseEntity<>(employeeMapper.toDTO(employeeEntity), HttpStatus.CREATED);
  }

  /**
   * Updates an existing employee.
   *
   * @param id The ID of the employee to update.
   * @param employeeDTO The EmployeeDTO containing the updated details.
   * @return A ResponseEntity containing the updated EmployeeDTO.
   */
  @PutMapping("/{id}")
  public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable final Long id, @Valid @RequestBody final EmployeeDTO employeeDTO) {
    log.debug("PUT /employees/{}", id);
    EmployeeEntity existingEmployee = employeeMapper.toEntity(employeeRetrievalService.findEmployeeDTOById(id));
    existingEmployee.setUsername(employeeDTO.getUsername());
    // In a real application, handle password updates carefully (e.g., check old password, hash new one)
    existingEmployee.setPassword(employeeDTO.getPassword());
    existingEmployee.setRole(employeeDTO.getRole());
    employeeUpdateService.update(existingEmployee);
    return ResponseEntity.ok(employeeMapper.toDTO(existingEmployee));
  }

  /**
   * Deletes an employee by its unique identifier.
   *
   * @param id The ID of the employee to delete.
   * @return A ResponseEntity with no content if deletion is successful.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable final Long id) {
    log.debug("DELETE /employees/{}", id);
    employeeDeletionService.permanentlyDelete(id);
    return ResponseEntity.noContent().build();
  }
}
