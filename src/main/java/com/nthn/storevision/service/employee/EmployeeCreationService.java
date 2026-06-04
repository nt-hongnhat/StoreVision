/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 11:21:37
 */

package com.nthn.storevision.service.employee;

import com.nthn.storevision.dto.EmployeeDTO;
import com.nthn.storevision.service.generic.Creatable;

/**
 * Service interface for creating and persisting employees.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see Creatable
 */
@FunctionalInterface
public interface EmployeeCreationService extends Creatable<EmployeeDTO> {}
