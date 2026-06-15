/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: VoucherNotFoundException.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:40:22
 */

package com.nthn.storevision.exception.voucher;

import com.nthn.storevision.constant.message.VoucherMessageCode;
import com.nthn.storevision.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Thrown when no voucher matches the supplied code. Maps to HTTP 404 Not Found. */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class VoucherNotFoundException extends ApplicationException {

  /**
   * Constructs a new {@code VoucherNotFoundException} for the given voucher code.
   *
   * @param voucherCode the code that did not match any voucher
   */
  public VoucherNotFoundException(final String voucherCode) {
    super(VoucherMessageCode.NOT_FOUND, voucherCode);
  }
}
