/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: AnalyticsMessageCode.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:10:07
 */

package com.nthn.storevision.constant.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * i18n message-code constants for the {@code analytics} domain. Prefix: {@code ANM}.
 *
 * <p>All codes correspond to entries in {@code /i18n/messages*.properties}.
 */
@Getter
@RequiredArgsConstructor
public enum AnalyticsMessageCode implements LocalizedMessageCode {
  /**
   * Represents the message code for an error where a reporting date range is invalid.
   *
   * <p>This code is used when the {@code from} date is after the {@code to} date (BR-A02).
   */
  INVALID_DATE_RANGE("ANM001");

  private final String code;
}
