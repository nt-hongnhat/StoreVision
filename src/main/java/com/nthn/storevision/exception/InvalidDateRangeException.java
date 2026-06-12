/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InvalidDateRangeException.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.exception;

import com.nthn.storevision.constant.message.AnalyticsMessageCode;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a reporting date range is invalid — {@code from} must be on or before {@code to}
 * (BR-A02). Maps to HTTP 400 Bad Request.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDateRangeException extends ApplicationException {

  /**
   * Constructs a new {@code InvalidDateRangeException} for the given range bounds.
   *
   * @param from the start of the rejected range
   * @param to the end of the rejected range
   */
  public InvalidDateRangeException(final LocalDate from, final LocalDate to) {
    super(AnalyticsMessageCode.INVALID_DATE_RANGE, from, to);
  }
}
