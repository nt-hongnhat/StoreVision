/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 16:07:28
 */

package com.nthn.storevision.service.employee.impl;

import com.nthn.storevision.dto.EmployeeDTO;
import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.EntityMapper;
import com.nthn.storevision.repository.EmployeeRepository;
import com.nthn.storevision.service.common.AbstractUpdatable;
import com.nthn.storevision.service.employee.EmployeeUpdateService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link EmployeeUpdateService} for persisting updates to {@link
 * EmployeeEntity} instances in the database.
 */
@Service
public class EmployeeUpdateServiceImpl
    extends AbstractUpdatable<EmployeeDTO, Long, EmployeeEntity, EmployeeRepository>
    implements EmployeeUpdateService {

  protected EmployeeUpdateServiceImpl(
      final EntityMapper<EmployeeDTO, Long, EmployeeEntity> mapper,
      final EmployeeRepository repository) {
    super(mapper, repository);
  }

  @Override
  protected EmployeeEntity getById(final Long id) {
    return getRepository()
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("employee.not.found", id));
  }
}
