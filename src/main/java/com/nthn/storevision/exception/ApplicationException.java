/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ApplicationException.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 20:29:02
 */

package com.nthn.storevision.exception;

import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Custom exception class for handling application-specific exceptions. Extends {@link
 * RuntimeException} to provide unchecked exception capabilities.
 */
@Getter
public class ApplicationException extends RuntimeException {

  private final Object[] args;

  private final String code;

  /**
   * Constructs a new {@code ApplicationException} with the specified code and arguments.
   *
   * @param code the error code associated with the exception
   * @param args optional arguments providing additional context for the exception
   */
  ApplicationException(final String code, final Object... args) {
    this.args = args;
    this.code = code;
  }

  /**
   * Constructs a new {@code ApplicationException} with the specified code, cause, and arguments.
   *
   * @param code the error code associated with the exception
   * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
   *     (A {@code null} value is permitted, and indicates that the cause is nonexistent or
   *     unknown.)
   * @param args optional arguments providing additional context for the exception
   */
  ApplicationException(final String code, final Throwable cause, final Object... args) {
    super(cause);
    this.args = args;
    this.code = code;
  }

  /**
   * Retrieves the localized error message for this exception using the provided {@link
   * MessageSource}.
   *
   * @param messageSource The {@link MessageSource} to use for resolving the message.
   * @return The localized error message.
   */
  public String getMessage(@NonNull final MessageSource messageSource) {
    return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
  }
}
