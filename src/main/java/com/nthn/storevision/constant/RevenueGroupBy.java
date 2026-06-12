/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: RevenueGroupBy.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:09:58
 */

package com.nthn.storevision.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the time bucket used to group revenue analytics. Each constant carries the matching
 * PostgreSQL {@code date_trunc} unit.
 */
@Getter
@RequiredArgsConstructor
public enum RevenueGroupBy {
  /** One bucket per calendar day. */
  DAY("day"),
  /** One bucket per ISO week. */
  WEEK("week"),
  /** One bucket per calendar month. */
  MONTH("month");

  /** The PostgreSQL {@code date_trunc} unit backing this bucket. */
  private final String truncUnit;
}
