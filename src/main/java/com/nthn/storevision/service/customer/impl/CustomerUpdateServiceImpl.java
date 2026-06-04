/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04
 */

package com.nthn.storevision.service.customer.impl;

import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.CustomerMapper;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.service.customer.CustomerUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link CustomerUpdateService} for persisting updates to
 * {@link CustomerEntity} instances in the database.
 */
@Service
@RequiredArgsConstructor
public class CustomerUpdateServiceImpl implements CustomerUpdateService {

  private final CustomerRepository repository;
  private final CustomerMapper mapper;

  @Transactional
  @Override
  public CustomerDTO update(final Long id, final CustomerDTO dto) {
    final CustomerEntity entity = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("customer.not.found", id));
    mapper.updateEntity(dto, entity);
    repository.save(entity);
    return mapper.toDTO(entity);
  }
}
