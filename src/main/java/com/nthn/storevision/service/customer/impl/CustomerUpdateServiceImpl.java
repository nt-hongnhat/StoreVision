/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: CustomerUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:39:29
 */

package com.nthn.storevision.service.customer.impl;

import com.nthn.storevision.constant.message.CustomerMessageCode;
import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.exception.customer.CustomerEmailAlreadyExistsException;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.service.common.AbstractUpdatable;
import com.nthn.storevision.service.customer.CustomerUpdateService;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link CustomerUpdateService} for persisting updates to {@link
 * CustomerEntity} instances in the database. Enforces BR-C01 (email uniqueness across the whole
 * system, excluding the record being updated) before persisting.
 */
@Slf4j
@Service
public class CustomerUpdateServiceImpl
    extends AbstractUpdatable<CustomerDTO, Long, CustomerEntity, CustomerRepository>
    implements CustomerUpdateService {

  protected CustomerUpdateServiceImpl(
      final EntityMapperConfig<CustomerDTO, Long, CustomerEntity> mapper,
      final CustomerRepository repository) {
    super(mapper, repository);
  }

  @Override
  @Transactional
  public CustomerDTO update(final Long id, final @NonNull CustomerDTO dto) {
    if (getRepository().isEmailTakenByOther(dto.getEmail(), id)) {
      log.warn(
          "BR-C01 violation on update: customer email '{}' already belongs to another customer",
          dto.getEmail());
      throw new CustomerEmailAlreadyExistsException(dto.getEmail());
    }
    return super.update(id, dto);
  }

  @Override
  protected CustomerEntity getById(final Long id) {
    return getRepository()
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(CustomerMessageCode.NOT_FOUND, id));
  }
}
