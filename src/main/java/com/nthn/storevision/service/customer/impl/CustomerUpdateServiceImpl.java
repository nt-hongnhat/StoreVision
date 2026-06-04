/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 16:32:19
 */

package com.nthn.storevision.service.customer.impl;

import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.EntityMapper;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.service.common.AbstractUpdatable;
import com.nthn.storevision.service.customer.CustomerUpdateService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link CustomerUpdateService} for persisting updates to {@link
 * CustomerEntity} instances in the database.
 */
@Service
public class CustomerUpdateServiceImpl
    extends AbstractUpdatable<CustomerDTO, Long, CustomerEntity, CustomerRepository>
    implements CustomerUpdateService {

  protected CustomerUpdateServiceImpl(
      final EntityMapper<CustomerDTO, Long, CustomerEntity> mapper,
      final CustomerRepository repository) {
    super(mapper, repository);
  }

  @Override
  protected CustomerEntity getById(final Long id) {
    return getRepository()
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("customer.not.found", id));
  }
}
