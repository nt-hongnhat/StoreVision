/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: InvalidOrderStateException.java
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
 * Thrown when a state transition (complete or cancel) is attempted on an order that is not in
 * {@code PENDING} status — completed and cancelled orders are final states (BR-O02). Maps to HTTP
 * 409 Conflict.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidOrderStateException extends ApplicationException {

  /**
   * Constructs a new {@code InvalidOrderStateException} for the given order and its current
   * status.
   *
   * @param orderId the ID of the order whose transition was rejected
   * @param currentStatus the status the order is currently in
   */
  public InvalidOrderStateException(final Long orderId, final OrderStatus currentStatus) {
    super(OrderMessageCode.INVALID_STATE, orderId, currentStatus);
  }
}
