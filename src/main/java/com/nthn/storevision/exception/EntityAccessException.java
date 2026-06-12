/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EntityAccessException.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 17:10:40
 */

package com.nthn.storevision.exception;

import com.nthn.storevision.constant.message.LocalizedMessageCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to indicate errors that occur while accessing or modifying entity fields. This
 * exception is typically thrown when there are issues with reflection or property access during
 * entity manipulation.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EntityAccessException extends ApplicationException {

  /**
   * Constructs a new {@code EntityAccessException} with the specified message code and arguments.
   *
   * @param messageKey the i18n message code identifying the error; must not be {@code null}
   * @param args optional arguments substituted into the localized message; may be empty
   */
  public EntityAccessException(final LocalizedMessageCode messageKey, final Object... args) {
    super(messageKey, args);
  }

  /**
   * Constructs a new {@code EntityAccessException} with the specified message code, cause, and
   * arguments.
   *
   * @param messageKey the i18n message code identifying the error; must not be {@code null}
   * @param cause the cause of the exception; may be {@code null} if nonexistent or unknown
   * @param args optional arguments substituted into the localized message; may be empty
   */
  public EntityAccessException(
      final LocalizedMessageCode messageKey, final Throwable cause, final Object... args) {
    super(messageKey, cause, args);
  }
}
