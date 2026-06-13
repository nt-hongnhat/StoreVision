/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationMessageCode.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 14:54:02
 */

package com.nthn.storevision.constant.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * i18n message-code constants for the {@code notification} domain. Prefix: {@code NOM}.
 *
 * <p>All codes correspond to entries in {@code /i18n/messages*.properties}.
 */
@Getter
@RequiredArgsConstructor
public enum NotificationMessageCode implements LocalizedMessageCode {
  /**
   * Represents the message code for a notification that could not be found.
   *
   * <p>This code is used when a notification with the given id does not exist.
   */
  NOT_FOUND("NOM001");

  private final String code;
}
