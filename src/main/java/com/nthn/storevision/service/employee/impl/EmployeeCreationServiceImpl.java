/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:10:04
 */

package com.nthn.storevision.service.employee.impl;

import com.nthn.storevision.dto.EmployeeDTO;
import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.mapper.EntityMapper;
import com.nthn.storevision.repository.EmployeeRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.employee.EmployeeCreationService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link EmployeeCreationService} that extends {@link AbstractCreatable}
 * to persist {@link EmployeeEntity} instances via {@link EmployeeRepository}.
 */
@Service
public class EmployeeCreationServiceImpl
    extends AbstractCreatable<EmployeeDTO, EmployeeEntity, EmployeeRepository>
    implements EmployeeCreationService {

  protected EmployeeCreationServiceImpl(
      final EntityMapper<EmployeeDTO, ?, EmployeeEntity> mapper, final EmployeeRepository repository) {
    super(mapper, repository);
  }
}
