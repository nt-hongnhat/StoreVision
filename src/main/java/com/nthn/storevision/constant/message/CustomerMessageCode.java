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
   * Represents the message code for an error where a customer entity is not found.
   *
   * <p>This code is used when a customer entity is not found in the system.
   */
  NOT_FOUND("CSM001");

  private final String code;
}
