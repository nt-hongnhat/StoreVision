/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 17:16:06
 */

package com.nthn.storevision.service.employee.impl;

import com.nthn.storevision.constant.message.EmployeeMessageCode;
import com.nthn.storevision.dto.employee.EmployeeDTO;
import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.EntityMapperConfig;
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
      final EntityMapperConfig<EmployeeDTO, Long, EmployeeEntity> mapper,
      final EmployeeRepository repository) {
    super(mapper, repository);
  }

  @Override
  protected EmployeeEntity getById(final Long id) {
    return getRepository()
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(EmployeeMessageCode.NOT_FOUND, id));
  }
}
