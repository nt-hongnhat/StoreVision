/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserMessage.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:19:12
 */

package com.nthn.storevision.constant.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing messages related to user-related operations. Each enum constant is associated
 * with a specific message string that can be used for logging or displaying user-facing messages.
 */
@Getter
@RequiredArgsConstructor
public enum UserMessage {
  /**
   * Represents a message indicating that a user could not be found. The message includes a format
   * string for specifying the user ID.
   */
  USER_NOT_FOUND("USM001");

  private final String code;
}
