/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02
 */

package com.nthn.storevision.service.employee.impl;

import com.nthn.storevision.dto.EmployeeDTO;
import com.nthn.storevision.service.employee.EmployeeCreationService;
import com.nthn.storevision.service.employee.EmployeeDeletionService;
import com.nthn.storevision.service.employee.EmployeeRetrievalService;
import com.nthn.storevision.service.employee.EmployeeService;
import com.nthn.storevision.service.employee.EmployeeUpdateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Aggregates all employee operation services into a single entry point for the controller layer.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeCreationService creationService;
  private final EmployeeDeletionService deletionService;
  private final EmployeeRetrievalService retrievalService;
  private final EmployeeUpdateService updateService;

  @Override
  @Transactional
  public EmployeeDTO create(final @NonNull EmployeeDTO dto) {
    log.debug("Creating employee with username: {}", dto.getUsername());
    final EmployeeDTO created = creationService.create(dto);
    log.debug("Employee created with id: {}", created.getId());
    return created;
  }

  @Override
  @Transactional(readOnly = true)
  public List<EmployeeDTO> findAll() {
    log.debug("Fetching all employees");
    return retrievalService.findAllEmployeesAsDTO();
  }

  @Override
  @Transactional(readOnly = true)
  public EmployeeDTO findById(final Long id) {
    log.debug("Fetching employee by id: {}", id);
    return retrievalService.findEmployeeDTOById(id);
  }

  @Override
  @Transactional
  public EmployeeDTO update(final Long id, final EmployeeDTO dto) {
    log.debug("Updating employee id: {}", id);
    final EmployeeDTO updated = updateService.update(id, dto);
    log.debug("Employee id: {} updated — role: {}", id, updated.getRole());
    return updated;
  }

  @Override
  @Transactional
  public void delete(final Long id) {
    log.debug("Deleting employee id: {}", id);
    deletionService.markAsDeleted(id);
    log.debug("Employee id: {} marked as deleted", id);
  }
}
