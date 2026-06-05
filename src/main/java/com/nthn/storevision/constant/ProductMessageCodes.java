/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductMessageCodes.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05
 */

package com.nthn.storevision.constant;

/** i18n message codes for the Product domain (prefix: PRM). */
public enum ProductMessageCodes {
  ;

  /** PRM001: product with given ID does not exist. */
  public static final String NOT_FOUND = "PRM001";

  /** PRM002: BR-P01 — name + category combination must be unique among active records. */
  public static final String ALREADY_EXISTS = "PRM002";

  /** PRM003: product name is a required field. */
  public static final String NAME_REQUIRED = "PRM003";

  /** PRM004: product price is a required field. */
  public static final String PRICE_REQUIRED = "PRM004";

  /** PRM005: product price must be greater than 0. */
  public static final String PRICE_MIN = "PRM005";

  /** PRM006: stock quantity is a required field. */
  public static final String STOCK_REQUIRED = "PRM006";

  /** PRM007: stock quantity must be >= 0. */
  public static final String STOCK_MIN = "PRM007";
}
