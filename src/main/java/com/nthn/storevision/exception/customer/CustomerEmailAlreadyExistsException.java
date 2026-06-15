/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: CustomerEmailAlreadyExistsException.java
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
 * Thrown when a customer with the same email already exists anywhere in the system, including on
 * soft-deleted records (BR-C01). Maps to HTTP 409 Conflict.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class CustomerEmailAlreadyExistsException extends ApplicationException {

  /**
   * Constructs a new {@code CustomerEmailAlreadyExistsException} for the given email address.
   *
   * @param email the email address that is already registered
   */
  public CustomerEmailAlreadyExistsException(final String email) {
    super(CustomerMessageCode.EMAIL_EXISTS, email);
  }
}
