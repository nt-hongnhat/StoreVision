/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerMessageCode.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 17:07:38
 */

package com.nthn.storevision.constant.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * i18n message-code constants for the {@code customer} domain. Prefix: {@code CSM}.
 *
 * <p>All codes correspond to entries in {@code /i18n/messages*.properties}.
 */
@Getter
@RequiredArgsConstructor
public enum CustomerMessageCode implements LocalizedMessageCode {
  /**
  /**
   * Represents the message code for an error where the loyalty point balance is insufficient.
   *
   * <p>This code is used when a redemption requests more points than the customer owns (BR-C03).
   */
  INSUFFICIENT_POINTS("CSM004"),
   * Represents the message code for an error where a customer entity is not found.
   *
   * <p>This code is used when a customer entity is not found in the system.
   */
  NOT_FOUND("CSM001");
  /**
   * Represents the message code for a validation error where redeemed points are less than 1.
   *
   * <p>This code is used when redemption requests fewer than 1 point, which is not allowed.
   */
  POINTS_MIN("CSM006"),
  /**
   * Represents the message code for a validation error where redeemed points are required.
   *
   * <p>This code is used when a redemption request is missing the number of points.
   */
  POINTS_REQUIRED("CSM005");

  private final String code;
}
