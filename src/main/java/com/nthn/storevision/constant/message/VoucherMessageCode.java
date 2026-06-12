/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherMessageCode.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 21:18:21
 */

package com.nthn.storevision.constant.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * i18n message-code constants for the {@code voucher} domain. Prefix: {@code VCM}.
 *
 * <p>All codes correspond to entries in {@code /i18n/messages*.properties}.
 */
@Getter
@RequiredArgsConstructor
public enum VoucherMessageCode implements LocalizedMessageCode {
  /**
   * Represents the message code for an error where a voucher has expired.
   *
   * <p>This code is used when the voucher's expiry date is before the current date (BR-V01).
   */
  EXPIRED("VCM002"),
  /**
   * Represents the message code for an error where a voucher is not active.
   *
   * <p>This code is used when the voucher's active flag is {@code false} (BR-V02).
   */
  INACTIVE("VCM003"),
  /**
   * Represents the message code for an error where the order amount is below the voucher minimum.
   *
   * <p>This code is used when the order amount does not reach the voucher's minimum order amount
   * (BR-V04).
   */
  MIN_ORDER_AMOUNT_NOT_MET("VCM005"),
  /**
   * Represents the message code for an error where a voucher entity is not found.
   *
   * <p>This code is used when no voucher matches the supplied code.
   */
  NOT_FOUND("VCM001"),
  /**
   * Represents the message code for an error where a voucher has reached its usage limit.
   *
   * <p>This code is used when the voucher's usage count has reached its maximum usage (BR-V03).
   */
  USAGE_LIMIT_REACHED("VCM004");

  private final String code;
}
