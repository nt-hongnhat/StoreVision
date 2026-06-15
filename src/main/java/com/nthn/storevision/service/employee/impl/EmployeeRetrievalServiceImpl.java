/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeRetrievalServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 12:04:55
 */

package com.nthn.storevision.service.employee.impl;

import com.nthn.storevision.constant.message.EmployeeMessageCode;
import com.nthn.storevision.constant.message.LocalizedMessageCode;
import com.nthn.storevision.dto.employee.EmployeeDTO;
import com.nthn.storevision.dto.response.PageDTO;
import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.mapper.EmployeeMapper;
import com.nthn.storevision.repository.EmployeeRepository; // Changed from JpaEmployeeRepository
import com.nthn.storevision.service.common.AbstractReadable;
import com.nthn.storevision.service.employee.EmployeeRetrievalService;
import com.nthn.storevision.util.PaginationUtil;
import java.util.Optional;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for retrieving {@link EmployeeEntity} entities. Extends {@link AbstractReadable} to
 * provide read operations for {@link EmployeeEntity} entities.
 */
@Slf4j
@Service
public class EmployeeRetrievalServiceImpl
    extends AbstractReadable<
        EmployeeEntity, Long, EmployeeRepository> // Changed from JpaEmployeeRepository
    implements EmployeeRetrievalService {

  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "username");

  private final EmployeeMapper employeeMapper;

  /**
   * Constructs a {@code EmployeeRetrievalServiceImpl} with the specified repository and mapper.
   *
   * @param repository the repository used for accessing {@link EmployeeEntity} entities
   * @param employeeMapper the mapper used for converting between entity and DTO
   */
  public EmployeeRetrievalServiceImpl(
      final EmployeeRepository repository,
      final EmployeeMapper employeeMapper) { // Changed from JpaEmployeeRepository
    super(repository);
    this.employeeMapper = employeeMapper;
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
  public <T> Optional<T> findByUsername(
      final String username, final Function<? super EmployeeEntity, ? extends T> mapper) {
    log.debug("Fetching employee by username: {}", username);
    return getRepository().findByUsername(username).map(mapper);
  }

  /**
   * Retrieves an employee by its identifier and maps it to {@link EmployeeDTO}.
   *
   * @param id the identifier of the employee to be retrieved.
   * @return the {@link EmployeeDTO} with the specified identifier, or null if not found.
   */
  @Override
  @Transactional(readOnly = true)
  public EmployeeDTO findEmployeeDTOById(final Long id) {
    log.debug("Fetching employee DTO by id: {}", id);
    return findById(id, employeeMapper::toDTO);
  }

  /**
   * Retrieves one page of active employees.
   *
   * @param page zero-based page index; negative values are treated as 0
   * @param size requested page size; clamped to the range [1, 100]
   * @return the requested page of {@link EmployeeDTO}s; never {@code null}
   */
  @Override
  @Transactional(readOnly = true)
  public PageDTO<EmployeeDTO> findEmployeesPage(final int page, final int size) {
    log.debug("Fetching employees page — page: {}, size: {}", page, size);
    return PageDTO.from(
        getRepository()
            .findAllActive(PaginationUtil.toPageRequest(page, size, DEFAULT_SORT))
            .map(employeeMapper::toDTO));
  }

  /**
   * Returns the message code for when an employee is not found.
   *
   * @return The message code for "employee not found".
   */
  @Override
  protected LocalizedMessageCode getNotFoundMessageCode() {
    return EmployeeMessageCode.NOT_FOUND;
  }
}
