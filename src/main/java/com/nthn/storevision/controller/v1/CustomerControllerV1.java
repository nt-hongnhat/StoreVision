/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 22:24:45
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.dto.response.ApiSuccessResponse;
import com.nthn.storevision.factory.ApiSuccessResponseFactory;
import com.nthn.storevision.service.customer.CustomerService;
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

/** REST controller for managing customers. */
@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerControllerV1 {

  private final CustomerService customerService;
  private final ApiSuccessResponseFactory successFactory;

  @PostMapping
  public ResponseEntity<ApiSuccessResponse<CustomerDTO>> createCustomer(
      @Valid @RequestBody final CustomerDTO dto, final HttpServletRequest request) {
    log.debug("POST /api/v1/customers");
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(wrap(request, customerService.create(dto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable final Long id) {
    log.debug("DELETE /api/v1/customers/{}", id);
    customerService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<ApiSuccessResponse<List<CustomerDTO>>> getAllCustomers(
      final HttpServletRequest request) {
    log.debug("GET /api/v1/customers");
    return ResponseEntity.ok(wrap(request, customerService.findAll()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<CustomerDTO>> getCustomerById(
      @PathVariable final Long id, final HttpServletRequest request) {
    log.debug("GET /api/v1/customers/{}", id);
    return ResponseEntity.ok(wrap(request, customerService.findById(id)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<CustomerDTO>> updateCustomer(
      @PathVariable final Long id,
      @Valid @RequestBody final CustomerDTO dto,
      final HttpServletRequest request) {
    log.debug("PUT /api/v1/customers/{}", id);
    return ResponseEntity.ok(wrap(request, customerService.update(id, dto)));
  }

  @SuppressWarnings("unchecked")
  private <T> ApiSuccessResponse<T> wrap(final HttpServletRequest request, final T data) {
    return (ApiSuccessResponse<T>) successFactory.create(request.getRequestURI(), data);
  }
}
