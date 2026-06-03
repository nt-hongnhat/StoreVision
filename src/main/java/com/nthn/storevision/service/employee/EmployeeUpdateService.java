/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeUpdateService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 11:21:37
 */

package com.nthn.storevision.service.employee;

import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.service.generic.Updatable;

/**
 * Service interface for updating {@link EmployeeEntity} entities.
 *
 * @see Updatable
 */
@FunctionalInterface
public interface EmployeeUpdateService extends Updatable<EmployeeEntity> {}
