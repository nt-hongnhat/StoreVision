/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryMessageCodes.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 17:02:10
 */

package com.nthn.storevision.constant.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * i18n message-code constants for the {@code inventory} domain. Prefix: {@code INM}.
 *
 * <p>All codes correspond to entries in {@code /i18n/messages*.properties}.
 */
@Getter
@RequiredArgsConstructor
public enum InventoryMessageCodes implements LocalizedMessageCodes {
  /**
   * Represents the message code for a validation error where a performer is required.
   *
   * <p>This code is used when a performer is missing or empty, which is a required field.
   */
  PERFORMER_REQUIRED("INM005"),
  /**
   * Represents the message code for a validation error where a product ID is required.
   *
   * <p>This code is used when a product ID is missing or empty, which is a required field.
   */
  PRODUCT_ID_REQUIRED("INM001"),
  /**
   * Represents the message code for a validation error where a quantity is less than 1.
   *
   * <p>This code is used when a quantity is less than 1, which is not allowed.
   */
  QUANTITY_MIN("INM003"),
  /**
   * Represents the message code for a validation error where a quantity is required.
   *
   * <p>This code is used when a quantity is missing or empty, which is a required field.
   */
  QUANTITY_REQUIRED("INM002"),
  /**
   * Represents the message code for a validation error where a unit cost is less than 1.
   *
   * <p>This code is used when a unit cost is less than 1, which is not allowed.
   */
  UNIT_COST_MIN("INM004");
  private final String code;
}
