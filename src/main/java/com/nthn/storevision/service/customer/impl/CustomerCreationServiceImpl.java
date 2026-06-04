/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:57:16
 */

package com.nthn.storevision.service.customer.impl;

import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.mapper.EntityMapper;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.customer.CustomerCreationService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link CustomerCreationService} that extends {@link AbstractCreatable}
 * to persist {@link CustomerEntity} instances via {@link CustomerRepository}.
 */
@Service
public class CustomerCreationServiceImpl
    extends AbstractCreatable<CustomerDTO, CustomerEntity, CustomerRepository>
    implements CustomerCreationService {

  protected CustomerCreationServiceImpl(final EntityMapper<CustomerDTO, ?, CustomerEntity> mapper, CustomerRepository repository) {
    super(mapper, repository);
  }
}
