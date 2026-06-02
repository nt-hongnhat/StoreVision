/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02
 */

package com.nthn.storevision.service.employee;

import com.nthn.storevision.dto.EmployeeDTO;
import java.util.List;

/** Single entry point for all employee business operations used by the controller layer. */
public interface EmployeeService {

  EmployeeDTO create(EmployeeDTO dto);

  List<EmployeeDTO> findAll();

  EmployeeDTO findById(Long id);

  EmployeeDTO update(Long id, EmployeeDTO dto);

  void delete(Long id);
}
