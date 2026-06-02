/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02
 */

package com.nthn.storevision.service.customer;

import com.nthn.storevision.dto.CustomerDTO;
import java.util.List;

/** Single entry point for all customer business operations used by the controller layer. */
public interface CustomerService {

  CustomerDTO create(CustomerDTO dto);

  List<CustomerDTO> findAll();

  CustomerDTO findById(Long id);

  CustomerDTO update(Long id, CustomerDTO dto);

  void delete(Long id);
}
