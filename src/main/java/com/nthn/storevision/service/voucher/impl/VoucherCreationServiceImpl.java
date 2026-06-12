/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 21:57:04
 */

package com.nthn.storevision.service.voucher.impl;

import com.nthn.storevision.dto.VoucherDTO;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.repository.VoucherRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.voucher.VoucherCreationService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link VoucherCreationService} that extends {@link AbstractCreatable}
 * to persist {@link VoucherEntity} instances via {@link VoucherRepository}.
 */
@Service
public class VoucherCreationServiceImpl
    extends AbstractCreatable<VoucherDTO, VoucherEntity, VoucherRepository>
    implements VoucherCreationService {

  protected VoucherCreationServiceImpl(
      final EntityMapperConfig<VoucherDTO, ?, VoucherEntity> mapper,
      final VoucherRepository repository) {
    super(mapper, repository);
  }
}
