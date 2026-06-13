/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherRetrievalServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 16:45:32
 */

package com.nthn.storevision.service.voucher.impl;

import com.nthn.storevision.dto.VoucherDTO;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.repository.VoucherRepository;
import com.nthn.storevision.service.common.AbstractReadable;
import com.nthn.storevision.service.voucher.VoucherRetrievalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link VoucherRetrievalService} that extends {@link AbstractReadable}
 * to retrieve {@link VoucherEntity} instances via {@link VoucherRepository}.
 */
@Service
public class VoucherRetrievalServiceImpl
    extends AbstractReadable<VoucherDTO, VoucherEntity, VoucherRepository>
    implements VoucherRetrievalService {

  protected VoucherRetrievalServiceImpl(
      final EntityMapperConfig<VoucherDTO, ?, VoucherEntity> mapper,
      final VoucherRepository repository) {
    super(mapper, repository);
  }

  @Override
  public Page<VoucherDTO> findVouchersPage(final Pageable pageable) {
    return getRepository().findAllActive(pageable).map(getMapper()::toDTO);
  }
}