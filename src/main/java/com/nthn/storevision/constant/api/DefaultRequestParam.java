/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: DefaultRequestParam.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 15:47:14
 */

package com.nthn.storevision.constant.api;

/**
 * Provides compile-time string constants for default parameter values used in request handling or
 * analytical computations.
 *
 * <p>This utility class is designed to centralize commonly used default settings, ensuring
 * consistency and reducing duplication in various components that rely on preset configurations or
 * fallback values. Each constant corresponds to a specific parameter or data grouping requirement,
 * typically applied when default behavior has not been overridden by user-provided input.
 */
public enum DefaultRequestParam {
  ;

  /**
   * The default value for the number of days, represented as a string. Typically used as a fallback
   * value or default parameter in cases where a specific number of days is required but not
   * provided.
   */
  public static final String DEFAULT_DAYS = "30";

  /**
   * Represents the default grouping unit used in revenue analytics. This value determines the time
   * interval for grouping data, typically applied in analytical queries to aggregate or organize
   * data based on a specific time unit.
   *
   * <p>The default setting is configured to group revenue data by day. It corresponds to the
   * PostgreSQL {@code date_trunc('day', timestamp)} function, which truncates timestamps to the
   * start of the corresponding day.
   *
   * <p>This constant is used as a fallback value in cases where no specific grouping unit is
   * provided.
   */
  public static final String DEFAULT_GROUP_BY = "day";

  /**
   * Defines the default limit for query results or data retrieval operations, represented as a
   * string. Typically used as a fallback value or default parameter in scenarios where a limit on
   * the number of results is required but not explicitly provided.
   *
   * <p>The default value is set to "10".
   */
  public static final String DEFAULT_LIMIT = "10";
  /** Default zero-based page index, as a string for {@code @RequestParam(defaultValue)}. */
  public static final String DEFAULT_PAGE = "0";

  /**
   * Represents the default time period used in various date-based operations or analytical queries
   * requiring a predefined time interval.
   *
   * <p>This constant defines the default truncation unit as "month" and corresponds to the
   * PostgreSQL {@code date_trunc('month', timestamp)} function, which truncates timestamps to the
   * beginning of the month.
   *
   * <p>It serves as a fallback or default parameter in scenarios where no specific time period is
   * provided.
   */
  public static final String DEFAULT_PERIOD = "month";
  /** Default page size, as a string for {@code @RequestParam(defaultValue)}. */
  public static final String DEFAULT_SIZE = "20";
}
