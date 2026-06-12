/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: GlobalExceptionHandler.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 17:12:24
 */

package com.nthn.storevision.advice;

import com.nthn.storevision.dto.response.Error;
import com.nthn.storevision.exception.ApplicationException;
import com.nthn.storevision.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler for handling application-specific exceptions. This class extends {@link
 * ResponseEntityExceptionHandler} to provide custom error responses.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessageSource messageSource;

  /**
   * Handles {@link ApplicationException} and returns an {@link Error} object with the appropriate
   * message.
   *
   * @param exception the {@link ApplicationException} that was thrown
   * @return an {@link Error} object containing the error code and message
   */
  @ExceptionHandler(ApplicationException.class)
  public Error handleCustomException(@NonNull final ApplicationException exception) {
    log.warn(
        "ApplicationException: [{}] {}",
        exception.getMessageKey(),
        exception.getMessage(messageSource));
    return createErrorFromException(exception);
  }

  /**
   * Handles {@link EntityNotFoundException} and returns an {@link Error} object with the
   * appropriate message.
   *
   * @param exception the {@link EntityNotFoundException} that was thrown
   * @return an {@link Error} object containing the error code and message
   */
  @ExceptionHandler(EntityNotFoundException.class)
  public Error handleEntityNotFoundException(@NonNull final EntityNotFoundException exception) {
    log.warn(
        "EntityNotFoundException: [{}] {}",
        exception.getMessageKey(),
        exception.getMessage(messageSource));
    return createErrorFromException(exception);
  }

  private @NonNull Error createErrorFromException(@NonNull final ApplicationException exception) {
    return new Error(exception.getMessageKey().getCode(), exception.getMessage(messageSource));
  }
}
