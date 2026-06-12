/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 17:15:08
 */

package com.nthn.storevision.service.customer.impl;

import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.customer.CustomerCreationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link CustomerCreationService} that extends {@link AbstractCreatable}
 * to persist {@link CustomerEntity} instances via {@link CustomerRepository}. Enforces BR-C01
 * (email uniqueness across the whole system, including soft-deleted records) before persisting.
 */
@Slf4j
@Service
public class CustomerCreationServiceImpl
    extends AbstractCreatable<CustomerDTO, CustomerEntity, CustomerRepository>
    implements CustomerCreationService {

  protected CustomerCreationServiceImpl(final EntityMapperConfig<CustomerDTO, ?, CustomerEntity> mapper, CustomerRepository repository) {
    super(mapper, repository);
  }
}
