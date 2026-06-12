/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InsufficientStockException.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.exception;

import com.nthn.storevision.constant.message.OrderMessageCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a product's available stock is lower than the quantity requested by an order
 * (BR-O01). Maps to HTTP 422 Unprocessable Content.
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
public class InsufficientStockException extends ApplicationException {

  /**
   * Constructs a new {@code InsufficientStockException} for the given product. This exception
   * indicates that the requested quantity exceeds the currently available stock, violating
   * business rule BR-O01 which requires sufficient stock for every order item.
   *
   * @param productName the name of the product whose stock is insufficient
   * @param available the quantity currently in stock
   * @param requested the quantity requested by the order
   */
  public InsufficientStockException(
      final String productName, final Integer available, final Integer requested) {
    super(OrderMessageCode.INSUFFICIENT_STOCK, productName, available, requested);
  }
}
