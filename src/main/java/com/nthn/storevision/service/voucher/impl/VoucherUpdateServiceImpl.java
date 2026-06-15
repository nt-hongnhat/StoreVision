/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13
 */

package com.nthn.storevision.service.voucher.impl;

import com.nthn.storevision.dto.voucher.VoucherDTO;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.repository.VoucherRepository;
import com.nthn.storevision.service.common.AbstractUpdatable;
import com.nthn.storevision.service.voucher.VoucherUpdateService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link VoucherUpdateService} that extends {@link AbstractUpdatable}
 * to update {@link VoucherEntity} instances via {@link VoucherRepository}.
 */
@Service
public class VoucherUpdateServiceImpl
    extends AbstractUpdatable<VoucherDTO, VoucherEntity, VoucherRepository>
    implements VoucherUpdateService {

  protected VoucherUpdateServiceImpl(
      final EntityMapperConfig<VoucherDTO, ?, VoucherEntity> mapper,
      final VoucherRepository repository) {
    super(mapper, repository);
  }
}