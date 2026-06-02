/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EntityNotFoundException.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 20:29:33
 */

package com.nthn.storevision.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an entity isn't found. This exception is annotated with {@link
 * ResponseStatus} to return a 404 NOT FOUND status.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends ApplicationException {

  /**
   * Constructs a new EntityNotFoundException with the specified code and arguments.
   *
   * @param code the error code associated with the exception
   * @param args optional arguments providing additional context for the exception
   */
  public EntityNotFoundException(final String code, final Object... args) {
    super(code, args);
  }
}
