/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: VoucherMinOrderAmountNotMetException.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:40:22
 */

package com.nthn.storevision.exception.voucher;

import com.nthn.storevision.constant.message.VoucherMessageCode;
import java.math.BigDecimal;

import com.nthn.storevision.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when the order amount does not reach the voucher's minimum order amount (BR-V04). Maps to
 * HTTP 422 Unprocessable Content.
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
public class VoucherMinOrderAmountNotMetException extends ApplicationException {

  /**
   * Constructs a new {@code VoucherMinOrderAmountNotMetException} for the given voucher code and
   * its required minimum order amount.
   *
   * @param voucherCode the code of the voucher whose minimum was not met
   * @param minOrderAmount the minimum order amount required by the voucher
   */
  public VoucherMinOrderAmountNotMetException(
      final String voucherCode, final BigDecimal minOrderAmount) {
    super(VoucherMessageCode.MIN_ORDER_AMOUNT_NOT_MET, voucherCode, minOrderAmount);
  }
}
