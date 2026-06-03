/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeDeletionService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 11:21:37
 */

package com.nthn.storevision.service.employee;

import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.service.generic.HardDeletable;
import com.nthn.storevision.service.generic.SoftDeletable;

/**
 * Service interface for soft-deleting, restoring, and permanently deleting
 * {@link EmployeeEntity} entities.
 *
 * @see SoftDeletable
 * @see HardDeletable
 */
public interface EmployeeDeletionService
    extends SoftDeletable<EmployeeEntity, Long>, HardDeletable<EmployeeEntity, Long> {}
