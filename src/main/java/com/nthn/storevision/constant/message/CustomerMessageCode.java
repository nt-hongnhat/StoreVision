/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerMessageCode.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:00:59
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
   * Represents the message code for a conflict where a customer email already exists.
   *
   * <p>This code is used when an attempt to create or update a customer fails because the email is
   * already registered, including on soft-deleted records (BR-C01).
   */
  EMAIL_EXISTS("CSM002"),
  /**
   * Represents the message code for a validation error where a customer email is invalid.
   *
   * <p>This code is used when the supplied email does not have a valid format.
   */
  EMAIL_INVALID("CSM009"),
  /**
   * Represents the message code for a validation error where a customer email is required.
   *
   * <p>This code is used when a customer email is missing or empty, which is a required field.
   */
  EMAIL_REQUIRED("CSM008"),
  /**
   * Represents the message code for an error where the loyalty point balance is insufficient.
   *
   * <p>This code is used when a redemption requests more points than the customer owns (BR-C03).
   */
  INSUFFICIENT_POINTS("CSM004"),
  /**
   * Represents the message code for a validation error where a customer name is required.
   *
   * <p>This code is used when a customer name is missing or empty, which is a required field.
   */
  NAME_REQUIRED("CSM007"),
  /**
   * Represents the message code for an error where a customer entity is not found.
   *
   * <p>This code is used when a customer entity is not found in the system.
   */
  NOT_FOUND("CSM001"),
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
