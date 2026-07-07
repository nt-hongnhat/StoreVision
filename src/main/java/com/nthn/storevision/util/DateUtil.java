/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: DateUtil.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-07-07 12:08:40
 */

package com.nthn.storevision.util;

import java.time.LocalDate;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;

/** Stateless date helpers shared across the domain. */
@Slf4j
@UtilityClass
public class DateUtil {

  /**
   * Tests whether the given date falls on the same month and day-of-month as the current date,
   * regardless of year — e.g., to detect a birthday or anniversary occurring today.
   *
   * @param date the date to test; must not be {@code null}
   * @return {@code true} if {@code date}'s month and day-of-month match today's; {@code false}
   *     otherwise
   */
  public boolean isAnniversaryToday(final @NonNull LocalDate date) {
    log.debug("Checking if the given date is an anniversary today");
    final LocalDate today = LocalDate.now();
    return date.getMonth() == today.getMonth() && date.getDayOfMonth() == today.getDayOfMonth();
  }
}
