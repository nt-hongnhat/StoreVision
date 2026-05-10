/*
 * Copyright (c) 2025 Hong Nhat. All rights reserved.
 *
 * Project: Book Thymeleaf
 * Module: Book Thymeleaf Main
 * File: EntityAccessException.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 22:36, 12/04/2025
 */

package com.nthn.storevision.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to indicate errors that occur while accessing or modifying entity fields.
 * This exception is typically thrown when there are issues with reflection or property access
 * during entity manipulation.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EntityAccessException extends ApplicationException {

  /**
   * Constructs a new {@code EntityAccessException} with the specified detail message.
   *
   * @param message the detail message.
   */
  public EntityAccessException(String message) {
    super(message);
  }

  /**
   * Constructs a new {@code EntityAccessException} with the specified detail message and cause.
   *
   * @param message the detail message.
   * @param cause the cause of the exception.
   */
  public EntityAccessException(String message, Throwable cause) {
    super(message, cause);
  }
}
