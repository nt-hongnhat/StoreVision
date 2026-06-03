/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.dto.EmployeeDTO;
import com.nthn.storevision.dto.response.ApiSuccessResponse;
import com.nthn.storevision.factory.ApiSuccessResponseFactory;
import com.nthn.storevision.service.employee.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for managing employees. */
@Slf4j
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeControllerV1 {

  private final ApiSuccessResponseFactory successFactory;
  private final EmployeeService employeeService;

  @PostMapping
  public ResponseEntity<ApiSuccessResponse<EmployeeDTO>> createEmployee(
      @Valid @RequestBody final EmployeeDTO dto, final HttpServletRequest request) {
    log.debug("POST /api/v1/employees");
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(wrap(request, employeeService.create(dto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable final Long id) {
    log.debug("DELETE /api/v1/employees/{}", id);
    employeeService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<ApiSuccessResponse<List<EmployeeDTO>>> getAllEmployees(
      final HttpServletRequest request) {
    log.debug("GET /api/v1/employees");
    return ResponseEntity.ok(wrap(request, employeeService.findAll()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<EmployeeDTO>> getEmployeeById(
      @PathVariable final Long id, final HttpServletRequest request) {
    log.debug("GET /api/v1/employees/{}", id);
    return ResponseEntity.ok(wrap(request, employeeService.findById(id)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<EmployeeDTO>> updateEmployee(
      @PathVariable final Long id,
      @Valid @RequestBody final EmployeeDTO dto,
      final HttpServletRequest request) {
    log.debug("PUT /api/v1/employees/{}", id);
    return ResponseEntity.ok(wrap(request, employeeService.update(id, dto)));
  }

  @SuppressWarnings("unchecked")
  private <T> ApiSuccessResponse<T> wrap(final HttpServletRequest request, final T data) {
    return (ApiSuccessResponse<T>) successFactory.create(request.getRequestURI(), data);
  }
}
