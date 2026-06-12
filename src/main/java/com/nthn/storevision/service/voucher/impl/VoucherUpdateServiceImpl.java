/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.service.voucher.impl;

import com.nthn.storevision.constant.message.VoucherMessageCode;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.repository.VoucherRepository;
import com.nthn.storevision.service.voucher.VoucherUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Default implementation of {@link VoucherUpdateService}. */
@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherUpdateServiceImpl implements VoucherUpdateService {

  private final VoucherRepository voucherRepository;

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void incrementUsage(final @NonNull Long voucherId) {
    final VoucherEntity voucher =
        voucherRepository
            .findById(voucherId)
            .orElseThrow(() -> new EntityNotFoundException(VoucherMessageCode.NOT_FOUND, voucherId));
    voucher.setUsageCount(voucher.getUsageCount() + 1);
    voucherRepository.save(voucher);
    log.debug("Voucher usage incremented: id={}, usageCount={}", voucherId, voucher.getUsageCount());
  }
}
