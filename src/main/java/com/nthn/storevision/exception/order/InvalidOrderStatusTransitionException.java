/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: InvalidOrderStatusTransitionException.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:51:59
 */

package com.nthn.storevision.exception.order;

import com.nthn.storevision.constant.order.OrderStatus;
import com.nthn.storevision.constant.message.OrderMessageCode;
import com.nthn.storevision.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when {@code PATCH /orders/{id}} is requested with a target {@link OrderStatus} that is not
 * a valid transition target — only {@code COMPLETED} and {@code CANCELLED} are accepted (BR-O02).
 * Maps to HTTP 400 Bad Request.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidOrderStatusTransitionException extends ApplicationException {

  /**
   * Constructs a new {@code InvalidOrderStatusTransitionException} for the given target status.
   *
   * @param targetStatus the requested target status that was rejected
   */
  public InvalidOrderStatusTransitionException(final OrderStatus targetStatus) {
    super(OrderMessageCode.INVALID_STATUS_TRANSITION, targetStatus);
  }
}
