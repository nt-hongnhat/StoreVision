/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04
 */

package com.nthn.storevision.service.employee.impl;

import com.nthn.storevision.dto.EmployeeDTO;
import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.EmployeeMapper;
import com.nthn.storevision.repository.EmployeeRepository;
import com.nthn.storevision.service.employee.EmployeeUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link EmployeeUpdateService} for persisting updates to
 * {@link EmployeeEntity} instances in the database.
 */
@Service
@RequiredArgsConstructor
public class EmployeeUpdateServiceImpl implements EmployeeUpdateService {

  private final EmployeeRepository repository;
  private final EmployeeMapper mapper;

  @Transactional
  @Override
  public EmployeeDTO update(final Long id, final EmployeeDTO dto) {
    final EmployeeEntity entity = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("employee.not.found", id));
    mapper.updateEntity(dto, entity);
    repository.save(entity);
    return mapper.toDTO(entity);
  }
}
