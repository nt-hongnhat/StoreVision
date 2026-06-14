/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: RevenueGroupBy.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 15:41:48
 */

package com.nthn.storevision.constant;

import com.nthn.storevision.constant.api.DefaultRequestParam;
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
  DAY(DefaultRequestParam.DEFAULT_GROUP_BY),
  /** One bucket per ISO week. */
  WEEK("week"),
  /** One bucket per calendar month. */
  MONTH(DefaultRequestParam.DEFAULT_PERIOD);

  /** The PostgreSQL {@code date_trunc} unit backing this bucket. */
  private final String truncUnit;
}
