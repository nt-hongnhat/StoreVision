/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderMessageCode.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 20:54:50
 */

package com.nthn.storevision.constant.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * i18n message-code constants for the {@code order} domain. Prefix: {@code ORM}.
 *
 * <p>All codes correspond to entries in {@code /i18n/messages*.properties}.
 */
@Getter
@RequiredArgsConstructor
public enum OrderMessageCode implements LocalizedMessageCode {
  /**
   * Represents the message code for a validation error where an order channel is required.
   *
   * <p>This code is used when the sales channel (POS or ONLINE) is missing from the request.
   */
  CHANNEL_REQUIRED("ORM005"),
  /**
   * Represents the message code for a validation error where a customer ID is required.
   *
   * <p>This code is used when the customer placing the order is missing from the request.
   */
  CUSTOMER_REQUIRED("ORM002"),
  /**
   * Represents the message code for an error where stock is insufficient for an order item.
   *
   * <p>This code is used when a product's available stock is lower than the requested quantity
   * (BR-O01).
   */
  INSUFFICIENT_STOCK("ORM004"),
  /**
   * Represents the message code for an error where an order is not in the expected state.
   *
   * <p>This code is used when a state transition (complete or cancel) is attempted on an order
   * that is not {@code PENDING} — completed and cancelled orders are final (BR-O02).
   */
  INVALID_STATE("ORM006"),
  /**
   * Represents the message code for a validation error where an order has no items.
   *
   * <p>This code is used when an order is created without at least one order item (BR-O05).
   */
  ITEMS_REQUIRED("ORM003"),
  /**
   * Represents the message code for an error where an order entity is not found.
   *
   * <p>This code is used when an order entity is not found in the system.
   */
  NOT_FOUND("ORM001");

  private final String code;
}
