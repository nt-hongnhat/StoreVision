/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: AnalyticsPeriod.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:51:59
 */

package com.nthn.storevision.constant.analytics;

import org.jspecify.annotations.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Defines the rolling look-back window for analytics queries such as top-selling products ({@code
 * period=today|week|month}).
 */
public enum AnalyticsPeriod {
  /** Since the start of the current day. */
  TODAY,
  /** The last 7 days. */
  WEEK,
  /** The last 30 days. */
  MONTH;

  private static final int MONTH_DAYS = 30;
  private static final int WEEK_DAYS = 7;

  /**
   * Resolves the inclusive start of this look-back window relative to the given day.
   *
   * @param today the reference day, usually the current date; must not be {@code null}
   * @return the window start as a {@link LocalDateTime}; never {@code null}
   */
  @NonNull
  public LocalDateTime resolveStart(final LocalDate today) {
    return switch (this) {
      case TODAY -> today.atStartOfDay();
      case WEEK -> today.minusDays(WEEK_DAYS).atStartOfDay();
      case MONTH -> today.minusDays(MONTH_DAYS).atStartOfDay();
    };
  }
}
