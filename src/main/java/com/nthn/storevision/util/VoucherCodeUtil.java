/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherCodeUtil.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-07-07 11:48:28
 */

package com.nthn.storevision.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;

/**
 * Stateless helper deciding whether a voucher code is present and non-blank, i.e. worth forwarding
 * to the voucher domain for full validation. A {@code null} or blank code is treated as absent.
 */
@Slf4j
@UtilityClass
public class VoucherCodeUtil {

  /**
   * Tests whether the supplied voucher code is present and non-blank.
   *
   * @param voucherCode the raw voucher code from the order request; may be {@code null}
   * @return {@code true} if the code is present and non-blank; {@code false} otherwise
   */
  public boolean isProvided(final @Nullable String voucherCode) {
    log.debug("Validating voucher code presence before voucher domain verification");
    if (null == voucherCode) {
      log.debug(
          "Voucher code presence validation completed with result=false because code is absent");
      return false;
    }
    if (voucherCode.isBlank()) {
      log.warn("Voucher code presence validation detected a blank voucher code input");
      return false;
    }
    log.debug("Voucher code presence validation completed with result=true");
    return true;
  }
}
