/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: CustomerHasPendingOrdersException.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:39:29
 */

package com.nthn.storevision.exception.customer;

import com.nthn.storevision.constant.message.CustomerMessageCode;
import com.nthn.storevision.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a customer deletion is rejected because the customer still has at least one {@code
 * PENDING} order — the orders must be canceled first (BR-C02). Maps to HTTP 409 Conflict.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class CustomerHasPendingOrdersException extends ApplicationException {

  /**
   * Constructs a new {@code CustomerHasPendingOrdersException} for the given customer.
   *
   * @param customerId the ID of the customer whose deletion was rejected
   */
  public CustomerHasPendingOrdersException(final Long customerId) {
    super(CustomerMessageCode.HAS_PENDING_ORDERS, customerId);
  }
}
