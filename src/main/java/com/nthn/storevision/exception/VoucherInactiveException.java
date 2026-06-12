/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherInactiveException.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.exception;

import com.nthn.storevision.constant.message.VoucherMessageCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Thrown when a voucher is not activated (BR-V02). Maps to HTTP 422 Unprocessable Content. */
@ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
public class VoucherInactiveException extends ApplicationException {

  /**
   * Constructs a new {@code VoucherInactiveException} for the given voucher code.
   *
   * @param voucherCode the code of the inactive voucher
   */
  public VoucherInactiveException(final String voucherCode) {
    super(VoucherMessageCode.INACTIVE, voucherCode);
  }
}
