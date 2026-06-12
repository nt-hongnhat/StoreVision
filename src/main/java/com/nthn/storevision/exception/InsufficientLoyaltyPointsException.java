/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InsufficientLoyaltyPointsException.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:00:59
 */

package com.nthn.storevision.exception;

import com.nthn.storevision.constant.message.CustomerMessageCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a loyalty point redemption requests more points than the customer's current balance —
 * the balance can never go negative (BR-C03). Maps to HTTP 422 Unprocessable Content.
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
public class InsufficientLoyaltyPointsException extends ApplicationException {

  /**
   * Constructs a new {@code InsufficientLoyaltyPointsException} with the current balance and the
   * requested amount.
   *
   * @param balance the customer's current point balance
   * @param requested the number of points the redemption asked for
   */
  public InsufficientLoyaltyPointsException(final Long balance, final Long requested) {
    super(CustomerMessageCode.INSUFFICIENT_POINTS, balance, requested);
  }
}
