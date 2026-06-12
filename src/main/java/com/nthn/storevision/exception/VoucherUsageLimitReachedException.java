/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherUsageLimitReachedException.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.exception;

import com.nthn.storevision.constant.message.VoucherMessageCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a voucher's usage count has reached its maximum usage (BR-V03). Maps to HTTP 422
 * Unprocessable Content.
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
public class VoucherUsageLimitReachedException extends ApplicationException {

  /**
   * Constructs a new {@code VoucherUsageLimitReachedException} for the given voucher code.
   *
   * @param voucherCode the code of the voucher that reached its usage limit
   */
  public VoucherUsageLimitReachedException(final String voucherCode) {
    super(VoucherMessageCode.USAGE_LIMIT_REACHED, voucherCode);
  }
}
