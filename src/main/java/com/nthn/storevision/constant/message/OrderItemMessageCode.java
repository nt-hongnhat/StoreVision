/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemMessageCode.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 20:54:55
 */

package com.nthn.storevision.constant.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * i18n message-code constants for the {@code orderitem} domain. Prefix: {@code OIM}.
 *
 * <p>All codes correspond to entries in {@code /i18n/messages*.properties}.
 */
@Getter
@RequiredArgsConstructor
public enum OrderItemMessageCode implements LocalizedMessageCode {
  /**
   * Represents the message code for an error where an order item entity is not found.
   *
   * <p>This code is used when an order item entity is not found in the system.
   */
  NOT_FOUND("OIM001"),
  /**
   * Represents the message code for a validation error where a product ID is required.
   *
   * <p>This code is used when an order item is missing its product reference.
   */
  PRODUCT_ID_REQUIRED("OIM002"),
  /**
   * Represents the message code for a validation error where a quantity is less than 1.
   *
   * <p>This code is used when an order item quantity is less than 1, which is not allowed.
   */
  QUANTITY_MIN("OIM004"),
  /**
   * Represents the message code for a validation error where a quantity is required.
   *
   * <p>This code is used when an order item quantity is missing, which is a required field.
   */
  QUANTITY_REQUIRED("OIM003");

  private final String code;
}
