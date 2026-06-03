/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeRetrievalServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 16:36:31
 */

package com.nthn.storevision.service.employee.impl;

import com.nthn.storevision.dto.EmployeeDTO;
import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.mapper.EmployeeMapper;
import com.nthn.storevision.repository.EmployeeRepository; // Changed from JpaEmployeeRepository
import com.nthn.storevision.service.common.AbstractReadable;
import com.nthn.storevision.service.employee.EmployeeRetrievalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Service for retrieving {@link EmployeeEntity} entities. Extends {@link AbstractReadable} to provide read
 * operations for {@link EmployeeEntity} entities.
 */
@Slf4j
@Service
public class EmployeeRetrievalServiceImpl
    extends AbstractReadable<EmployeeEntity, Long, EmployeeRepository> // Changed from JpaEmployeeRepository
    implements EmployeeRetrievalService {

  private final EmployeeMapper employeeMapper;

  /**
   * Constructs a {@code EmployeeRetrievalServiceImpl} with the specified repository and mapper.
   *
   * @param repository the repository used for accessing {@link EmployeeEntity} entities
   * @param employeeMapper the mapper used for converting between entity and DTO
   */
  public EmployeeRetrievalServiceImpl(final EmployeeRepository repository, final EmployeeMapper employeeMapper) { // Changed from JpaEmployeeRepository
    super(repository);
    this.employeeMapper = employeeMapper;
  }

  /**
   * Retrieves all employees and maps them to {@link EmployeeDTO}.
   *
   * @return a list of all {@link EmployeeDTO}s.
   */
  @Override
  @Transactional(readOnly = true)
  public List<EmployeeDTO> findAllEmployeesAsDTO() {
    log.debug("Fetching all employees as DTO");
    return findAll(employeeMapper::toDTO);
  }

  /**
   * Retrieves an employee by its identifier and maps it to {@link EmployeeDTO}.
   *
   * @param id the identifier of the employee to be retrieved.
   * @return the {@link EmployeeDTO} with the specified identifier, or null if not found.
   */
  @Override
  @Transactional(readOnly = true)
  public EmployeeDTO findEmployeeDTOById(Long id) {
    log.debug("Fetching employee DTO by id: {}", id);
    return findById(id, employeeMapper::toDTO);
  }

  /**
   * Finds an employee by their username and maps the result using the provided mapping function.
   * This operation is read-only and transactional.
   *
   * @param username the username of the employee to retrieve
   * @param mapper a function to map the retrieved employee instance into the desired type
   * @param <T> the return type after applying the mapper function
   * @return an {@code Optional} containing the mapped result if the employee is found, or an empty
   *     {@code Optional} if no employee corresponds to the given username
   */
  @Override
  @Transactional(readOnly = true)
  public <T> Optional<T> findByUsername(String username, Function<? super EmployeeEntity, T> mapper) {
    log.debug("Fetching employee by username: {}", username);
    return getRepository().findByUsername(username).map(mapper);
  }

  /**
   * Returns the message code for when an employee is not found.
   *
   * @return The message code for "employee not found".
   */
  @Override
  protected String getNotFoundMessageCode() {
    // Placeholder, replace with actual message code if available
    return "employee.not.found";
  }
}
