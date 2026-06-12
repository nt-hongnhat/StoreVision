/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherValidationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.service.voucher.impl;

import com.nthn.storevision.dto.VoucherDTO;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.exception.VoucherExpiredException;
import com.nthn.storevision.exception.VoucherInactiveException;
import com.nthn.storevision.exception.VoucherMinOrderAmountNotMetException;
import com.nthn.storevision.exception.VoucherNotFoundException;
import com.nthn.storevision.exception.VoucherUsageLimitReachedException;
import com.nthn.storevision.mapper.VoucherMapper;
import com.nthn.storevision.repository.VoucherRepository;
import com.nthn.storevision.service.voucher.VoucherValidationService;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link VoucherValidationService}. Applies the voucher rules in order:
 * existence, expiry (BR-V01), activation (BR-V02), usage limit (BR-V03), minimum order amount
 * (BR-V04). Soft-deleted vouchers are treated as not found.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherValidationServiceImpl implements VoucherValidationService {

  private final VoucherMapper voucherMapper;
  private final VoucherRepository voucherRepository;

  /** {@inheritDoc} */
  @Override
  @Transactional(readOnly = true)
  public VoucherDTO validate(
      final @NonNull String voucherCode, final @NonNull BigDecimal orderAmount) {
    log.debug("Validating voucher: code={}, orderAmount={}", voucherCode, orderAmount);

    final VoucherEntity voucher =
        voucherRepository
            .findByCode(voucherCode)
            .filter(found -> found.getDeletedDate().isEmpty())
            .orElseThrow(() -> new VoucherNotFoundException(voucherCode));

    // BR-V01: the voucher must not be expired
    if (voucher.getExpiryDate().isBefore(LocalDate.now())) {
      throw new VoucherExpiredException(voucherCode);
    }
    // BR-V02: the voucher must be activated
    if (!voucher.isActive()) {
      throw new VoucherInactiveException(voucherCode);
    }
    // BR-V03: the voucher must not have reached its usage limit
    if (voucher.getUsageCount() >= voucher.getMaxUsage()) {
      throw new VoucherUsageLimitReachedException(voucherCode);
    }
    // BR-V04: the order amount must reach the voucher minimum, when one is defined
    if (null != voucher.getMinOrderAmount()
        && 0 > orderAmount.compareTo(voucher.getMinOrderAmount())) {
      throw new VoucherMinOrderAmountNotMetException(voucherCode, voucher.getMinOrderAmount());
    }

    log.debug("Voucher valid: code={}, type={}", voucherCode, voucher.getDiscountType());
    return voucherMapper.toDTO(voucher);
  }
}
