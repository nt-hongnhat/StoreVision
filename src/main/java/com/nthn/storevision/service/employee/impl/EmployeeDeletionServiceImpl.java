/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeDeletionServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04
 */

package com.nthn.storevision.service.employee.impl;

import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.repository.EmployeeRepository;
import com.nthn.storevision.service.common.AbstractDeletable;
import com.nthn.storevision.service.employee.EmployeeDeletionService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link EmployeeDeletionService} that soft-deletes
 * {@link EmployeeEntity} instances by setting the {@code isDeleted} flag, which triggers
 * {@code CustomAuditingEntityListener} to populate {@code deletedBy} and {@code deletedDate}.
 */
@Service
public class EmployeeDeletionServiceImpl
    extends AbstractDeletable<EmployeeEntity, Long, EmployeeRepository>
    implements EmployeeDeletionService {

  protected EmployeeDeletionServiceImpl(final EmployeeRepository repository) {
    super(repository);
  }
}
