/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeUpdateService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 16:07:28
 */

package com.nthn.storevision.service.employee;

import com.nthn.storevision.dto.employee.EmployeeDTO;
import com.nthn.storevision.service.generic.Updatable;

/**
 * Service interface for updating employees.
 *
 * @see Updatable
 */
@FunctionalInterface
public interface EmployeeUpdateService extends Updatable<EmployeeDTO, Long> {}
