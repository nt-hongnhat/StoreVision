/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherValidationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.service.voucher;

import com.nthn.storevision.dto.VoucherDTO;
import java.math.BigDecimal;
import org.jspecify.annotations.NonNull;

/**
 * Service interface for validating a voucher before it is applied to an order.
 *
 * <p>Enforces the voucher business rules: BR-V01 (not expired), BR-V02 (active), BR-V03 (usage
 * limit not reached) and BR-V04 (minimum order amount met).
 */
@FunctionalInterface
public interface VoucherValidationService {

  /**
   * Validates the voucher identified by {@code voucherCode} against the given order amount and
   * returns it when all rules pass.
   *
   * @param voucherCode the code of the voucher to validate; must not be {@code null}
   * @param orderAmount the order amount the voucher is applied to; must not be {@code null}
   * @return the validated voucher as a {@link VoucherDTO}; never {@code null}
   * @throws com.nthn.storevision.exception.VoucherNotFoundException if no active voucher matches
   *     the code
   * @throws com.nthn.storevision.exception.VoucherExpiredException if the voucher expired (BR-V01)
   * @throws com.nthn.storevision.exception.VoucherInactiveException if the voucher is deactivated
   *     (BR-V02)
   * @throws com.nthn.storevision.exception.VoucherUsageLimitReachedException if the voucher
   *     reached its usage limit (BR-V03)
   * @throws com.nthn.storevision.exception.VoucherMinOrderAmountNotMetException if the order
   *     amount is below the voucher minimum (BR-V04)
   */
  VoucherDTO validate(@NonNull String voucherCode, @NonNull BigDecimal orderAmount);
}
