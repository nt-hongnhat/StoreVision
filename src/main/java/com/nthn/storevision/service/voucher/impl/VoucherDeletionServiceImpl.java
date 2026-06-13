/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherDeletionServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 16:46:48
 */

package com.nthn.storevision.service.voucher.impl;

import com.nthn.storevision.dto.VoucherDTO;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.repository.VoucherRepository;
import com.nthn.storevision.service.common.AbstractSoftDeletable;
import com.nthn.storevision.service.voucher.VoucherDeletionService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link VoucherDeletionService} that extends {@link AbstractSoftDeletable}
 * to soft-delete and restore {@link VoucherEntity} instances via {@link VoucherRepository}.
 */
@Service
public class VoucherDeletionServiceImpl
    extends AbstractSoftDeletable<VoucherDTO, VoucherEntity, VoucherRepository>
    implements VoucherDeletionService {

  protected VoucherDeletionServiceImpl(
      final EntityMapperConfig<VoucherDTO, ?, VoucherEntity> mapper,
      final VoucherRepository repository) {
    super(mapper, repository);
  }
}