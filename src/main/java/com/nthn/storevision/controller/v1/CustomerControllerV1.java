/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:15:54
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.mapper.CustomerMapper;
import com.nthn.storevision.service.customer.CustomerCreationService;
import com.nthn.storevision.service.customer.CustomerDeletionService;
import com.nthn.storevision.service.customer.CustomerRetrievalService;
import com.nthn.storevision.service.customer.CustomerUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing customers.
 * Provides endpoints for CRUD operations on customers.
 */
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerControllerV1 {

  private final CustomerCreationService customerCreationService;
  private final CustomerRetrievalService customerRetrievalService;
  private final CustomerUpdateService customerUpdateService;
  private final CustomerDeletionService customerDeletionService;
  private final CustomerMapper customerMapper;

  /**
   * Retrieves a list of all customers.
   *
   * @return A ResponseEntity containing a list of CustomerDTOs.
   */
  @GetMapping
  public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
    return ResponseEntity.ok(customerRetrievalService.findAllCustomersAsDTO());
  }

  /**
   * Retrieves a customer by its unique identifier.
   *
   * @param id The ID of the customer to retrieve.
   * @return A ResponseEntity containing the CustomerDTO if found, or a not found status.
   */
  @GetMapping("/{id}")
  public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable final Long id) {
    return ResponseEntity.ok(customerRetrievalService.findCustomerDTOById(id));
  }

  /**
   * Creates a new customer.
   *
   * @param customerDTO The CustomerDTO containing the details of the customer to create.
   * @return A ResponseEntity containing the created CustomerDTO.
   */
  @PostMapping
  public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody final CustomerDTO customerDTO) {
    CustomerEntity customerEntity = customerMapper.toEntity(customerDTO);
    customerCreationService.create(customerEntity);
    return new ResponseEntity<>(customerMapper.toDTO(customerEntity), HttpStatus.CREATED);
  }

  /**
   * Updates an existing customer.
   *
   * @param id The ID of the customer to update.
   * @param customerDTO The CustomerDTO containing the updated details.
   * @return A ResponseEntity containing the updated CustomerDTO.
   */
  @PutMapping("/{id}")
  public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable final Long id, @Valid @RequestBody final CustomerDTO customerDTO) {
    CustomerEntity existingCustomer = customerMapper.toEntity(customerRetrievalService.findCustomerDTOById(id));
    existingCustomer.setName(customerDTO.getName());
    existingCustomer.setEmail(customerDTO.getEmail());
    existingCustomer.setPhone(customerDTO.getPhone());
    existingCustomer.setAddress(customerDTO.getAddress());
    customerUpdateService.update(existingCustomer);
    return ResponseEntity.ok(customerMapper.toDTO(existingCustomer));
  }

  /**
   * Deletes a customer by its unique identifier.
   *
   * @param id The ID of the customer to delete.
   * @return A ResponseEntity with no content if deletion is successful.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable final Long id) {
    customerDeletionService.permanentlyDelete(id);
    return ResponseEntity.noContent().build();
  }
}
