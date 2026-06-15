/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: DiscountType.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:51:59
 */

package com.nthn.storevision.constant.voucher;

/** Defines how a voucher's discount value is interpreted. */
public enum DiscountType {
  /** The discount value is a percentage of the order subtotal, optionally capped. */
  PERCENTAGE,
  /** The discount value is a fixed amount in VND. */
  FIXED_AMOUNT
}
