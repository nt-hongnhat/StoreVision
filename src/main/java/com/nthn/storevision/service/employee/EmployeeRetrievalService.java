/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeRetrievalService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:28:36
 */

package com.nthn.storevision.service.employee;

import com.nthn.storevision.dto.EmployeeDTO;
import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.service.generic.Readable;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Service interface for retrieving {@link EmployeeEntity} entities.
 *
 * <p>Extends the {@link Readable} interface to provide common read operations for {@link
 * EmployeeEntity} entities.
 */
public interface EmployeeRetrievalService extends Readable<EmployeeEntity, Long> {

  /**
   * Retrieves all employees and maps them to {@link EmployeeDTO}.
   *
   * @return a list of all {@link EmployeeDTO}s.
   */
  List<EmployeeDTO> findAllEmployeesAsDTO();

  /**
   * Retrieves an employee by its identifier and maps it to {@link EmployeeDTO}.
   *
   * @param id the identifier of the employee to be retrieved.
   * @return the {@link EmployeeDTO} with the specified identifier, or null if not found.
   */
  EmployeeDTO findEmployeeDTOById(Long id);

  /**
   * Finds an employee by their username and maps the result using the provided mapping function.
   *
   * @param username the username of the employee to retrieve
   * @param mapper a function to map the retrieved employee instance into the desired type
   * @param <T> the return type after applying the mapper function
   * @return an {@code Optional} containing the mapped result if the employee is found, or an empty
   *     {@code Optional} if no employee corresponds to the given username
   */
  <T> Optional<T> findByUsername(String username, Function<? super EmployeeEntity, T> mapper);
}
