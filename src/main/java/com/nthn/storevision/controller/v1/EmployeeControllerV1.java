/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 15:47:14
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.*;
import com.nthn.storevision.controller.EmployeeController;
import com.nthn.storevision.dto.employee.EmployeeDTO;
import com.nthn.storevision.service.employee.EmployeeCreationService;
import com.nthn.storevision.service.employee.EmployeeDeletionService;
import com.nthn.storevision.service.employee.EmployeeRetrievalService;
import com.nthn.storevision.service.employee.EmployeeUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Version 1 implementation of {@link EmployeeController}. */
@Tag(name = "Employee", description = "Employee management endpoints")
@Slf4j
@RestController
@RequestMapping(value = EndpointDefinitions.EMPLOYEES, version = "v1")
@RequiredArgsConstructor
public class EmployeeControllerV1 implements EmployeeController {

  private final EmployeeCreationService creationService;
  private final EmployeeDeletionService deletionService;
  private final EmployeeRetrievalService retrievalService;
  private final EmployeeUpdateService updateService;

  @Operation(
      summary = "Create a new employee",
      description = OperationDescription.EMPLOYEE_CREATION)
  @ApiResponse(responseCode = ResponseCode.CREATED, description = ResponseDescription.CREATED)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @Override
  public EmployeeDTO createEmployee(@Valid @RequestBody final EmployeeDTO dto) {
    log.debug("POST /employees");
    return creationService.create(dto);
  }

  @Operation(
      summary = "Soft-delete an employee by ID",
      description = OperationDescription.EMPLOYEE_DELETION)
  @ApiResponse(responseCode = ResponseCode.NO_CONTENT, description = ResponseDescription.DELETED)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  @Override
  public void deleteEmployee(
      @Parameter(description = ParameterDescription.EMPLOYEE_ID) @PathVariable final Long id) {
    log.debug("DELETE /employees/{}", id);
    deletionService.markAsDeleted(id);
  }

  @Operation(
      summary = "Retrieve employees (paginated)",
      description = OperationDescription.EMPLOYEE_LIST)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping
  @Override
  public Page<EmployeeDTO> getAllEmployees(
      @Parameter(description = ParameterDescription.PAGE_INDEX)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_PAGE)
          final int page,
      @Parameter(description = ParameterDescription.PAGE_SIZE)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_SIZE)
          final int size) {
    log.debug("GET /employees — page: {}, size: {}", page, size);
    return retrievalService.findEmployeesPage(page, size);
  }

  @Operation(
      summary = "Retrieve an employee by ID",
      description = OperationDescription.EMPLOYEE_GET_BY_ID)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @GetMapping("/{id}")
  @Override
  public EmployeeDTO getEmployeeById(
      @Parameter(description = ParameterDescription.EMPLOYEE_ID) @PathVariable final Long id) {
    log.debug("GET /employees/{}", id);
    return retrievalService.findEmployeeDTOById(id);
  }

  @Operation(
      summary = "Update an employee by ID",
      description = OperationDescription.EMPLOYEE_UPDATE)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PutMapping("/{id}")
  @Override
  public EmployeeDTO updateEmployee(
      @Parameter(description = ParameterDescription.EMPLOYEE_ID) @PathVariable final Long id,
      @Valid @RequestBody final EmployeeDTO dto) {
    log.debug("PUT /employees/{}", id);
    return updateService.update(id, dto);
  }
}
