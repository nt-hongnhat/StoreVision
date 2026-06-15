/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductNameAlreadyExistsException.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 17:09:31
 */

package com.nthn.storevision.exception.product;

import com.nthn.storevision.constant.message.ProductMessageCode;
import com.nthn.storevision.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a product with the same name already exists in the same category (BR-P01). Maps to
 * HTTP 409 Conflict.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ProductNameAlreadyExistsException extends ApplicationException {

  /**
   * Constructs a new {@code ProductNameAlreadyExistsException} with the specified product name and
   * category. This constructor creates an exception indicating that a product with the given name
   * already exists in the specified category, violating business rule BR-P01 which requires product
   * name and category combinations to be unique among active records.
   *
   * @param name the name of the product that already exists
   * @param category the category in which the product name conflict occurred
   */
  public ProductNameAlreadyExistsException(final String name, final String category) {
    super(ProductMessageCode.ALREADY_EXISTS, name, category);
  }
}
