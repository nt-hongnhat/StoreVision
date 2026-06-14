/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductMessageCode.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 17:07:38
 */

package com.nthn.storevision.constant.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** i18n message codes for the Product domain (prefix: PRM). */
@Getter
@RequiredArgsConstructor
public enum ProductMessageCode implements LocalizedMessageCode {
  /**
   * Represents the message code for a conflict where a product entity already exists.
   *
   * <p>This code is used in scenarios where an attempt to create or add a product fails because an
   * entity with the same unique attributes already exists in the system.
   */
  ALREADY_EXISTS("PRM002"),
  /**
   * Represents the message code for a validation error where the {@code deleted} field of a
   * restore request is not {@code false}.
   *
   * <p>This code is used when {@code PATCH /products/{id}} is called with {@code deleted: true} —
   * restoring a product only supports clearing the soft-delete markers; use {@code DELETE
   * /products/{id}} to soft-delete it instead.
   */
  DELETED_MUST_BE_FALSE("PRM008"),
  /**
   * Represents the message code for a validation error where a product name is required.
   *
   * <p>This code is used when a product name is missing or empty, which is a required field.
   */
  NAME_REQUIRED("PRM003"),
  /**
   * Represents the message code for a validation error where a product entity is not found.
   *
   * <p>This code is used when a product entity is not found in the system.
   */
  NOT_FOUND("PRM001"),
  /**
   * Represents the message code for a validation error where a product price is less than zero.
   *
   * <p>This code is used when a product price is negative, which is not allowed.
   */
  PRICE_MIN("PRM005"),

  /**
   * Represents the message code for a validation error where a product price is required.
   *
   * <p>This code is used when a product price is missing or empty, which is a required field.
   */
  PRICE_REQUIRED("PRM004"),
  /**
   * Represents the message code for a validation error where a product stock quantity is less than
   * zero.
   *
   * <p>This code is used when a product stock quantity is negative, which is not allowed.
   */
  STOCK_MIN("PRM007"),
  /**
   * Represents the message code for a validation error where a product stock quantity is required.
   *
   * <p>This code is used when a product stock quantity is missing or empty, which is a required
   * field.
   */
  STOCK_REQUIRED("PRM006");

  private final String code;
}
