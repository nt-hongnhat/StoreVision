/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EntityAccessException.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 20:29:20
 */

package com.nthn.storevision.exception;

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
   * Constructs a new {@code EntityAccessException} with the specified detail message.
   *
   * @param code the detail message.
   */
  public EntityAccessException(final String code) {
    super(code);
  }

  /**
   * Constructs a new {@code EntityAccessException} with the specified detail message and cause.
   *
   * @param code the detail message.
   * @param cause the cause of the exception.
   */
  public EntityAccessException(final String code, final Throwable cause) {
    super(code, cause);
  }
}
