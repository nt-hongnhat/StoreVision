/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02
 */

package com.nthn.storevision.service.customer.impl;

import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.mapper.CustomerMapper;
import com.nthn.storevision.service.customer.CustomerCreationService;
import com.nthn.storevision.service.customer.CustomerDeletionService;
import com.nthn.storevision.service.customer.CustomerRetrievalService;
import com.nthn.storevision.service.customer.CustomerService;
import com.nthn.storevision.service.customer.CustomerUpdateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Aggregates all customer operation services into a single entry point for the controller layer.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerCreationService creationService;
  private final CustomerDeletionService deletionService;
  private final CustomerMapper mapper;
  private final CustomerRetrievalService retrievalService;
  private final CustomerUpdateService updateService;

  @Override
  @Transactional
  public CustomerDTO create(final CustomerDTO dto) {
    log.debug("Creating customer with email: {}", dto.getEmail());
    final CustomerEntity entity = mapper.toEntity(dto);
    creationService.create(entity);
    log.debug("Customer created with id: {}", entity.getId());
    return mapper.toDTO(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public List<CustomerDTO> findAll() {
    log.debug("Fetching all customers");
    return retrievalService.findAllCustomersAsDTO();
  }

  @Override
  @Transactional(readOnly = true)
  public CustomerDTO findById(final Long id) {
    log.debug("Fetching customer by id: {}", id);
    return retrievalService.findCustomerDTOById(id);
  }

  @Override
  @Transactional
  public CustomerDTO update(final Long id, final CustomerDTO dto) {
    log.debug("Updating customer id: {}", id);
    final CustomerDTO updated = updateService.update(id, dto);
    log.debug("Customer id: {} updated successfully", id);
    return updated;
  }

  @Override
  @Transactional
  public void delete(final Long id) {
    log.debug("Deleting customer id: {}", id);
    deletionService.markAsDeleted(id);
    log.debug("Customer id: {} marked as deleted", id);
  }
}
