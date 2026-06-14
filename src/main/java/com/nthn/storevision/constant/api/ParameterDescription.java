/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ParameterDescription.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 15:36:12
 */

package com.nthn.storevision.constant.api;

/**
 * Provides string constants representing descriptions for various parameters used in RESTful API
 * endpoints. This enum serves as a centralized location for maintaining parameter-related
 * documentation, ensuring consistency across the codebase.
 */
public enum ParameterDescription {
  ;

  /**
   * Describes the inclusive start of a reporting date range. Defaults to the 1st day of the current
   * month when omitted (BR-A02).
   */
  public static final String ANALYTICS_FROM_DATE =
      "Inclusive range start (ISO date); defaults to the 1st of the current month (BR-A02)";
  /** Describes the maximum number of rows a ranked analytics report may return. */
  public static final String ANALYTICS_LIMIT = "Maximum number of rows to return";
  /**
   * Describes the length, in days, of the look-back window used by the slow-moving products report.
   */
  public static final String ANALYTICS_LOOKBACK_DAYS = "Length of the look-back window in days";
  /**
   * Describes the rolling look-back window (today, week, or month) used by the top-selling products
   * report.
   */
  public static final String ANALYTICS_PERIOD = "Rolling look-back window: today, week or month";
  /** Describes the bucket size (day, week, or month) used to group the revenue report. */
  public static final String ANALYTICS_REVENUE_GROUP_BY = "Bucket size: day, week or month";
  /**
   * Describes the inclusive end of a reporting date range. Defaults to the last day of the current
   * month when omitted (BR-A02).
   */
  public static final String ANALYTICS_TO_DATE =
      "Inclusive range end (ISO date); defaults to the last day of the current month (BR-A02)";
  /**
   * Represents the description for an identifier used to specify a customer. Used in API response
   * descriptions to provide clarity on the meaning of the customer ID field.
   */
  public static final String CUSTOMER_ID = "Customer ID";

  /**
   * Represents the description for an identifier used to specify an employee. Used in API response
   * descriptions to provide clarity on the meaning of the employee ID field.
   */
  public static final String EMPLOYEE_ID = "Employee ID";

  /**
   * Error message indicating that the customer does not have enough points in their balance to
   * fulfill the request. This constant is typically used to represent a response or validation
   * error in scenarios involving loyalty points or rewards systems.
   */
  public static final String INSUFFICIENT_POINTS =
      "Requested points exceed the customer's current balance (BR-C03)";

  /**
   * Represents the description for an identifier used to specify a notification. Used in API
   * response descriptions to provide clarity on the meaning of the notification ID field.
   */
  public static final String NOTIFICATION_ID = "Notification ID";

  /**
   * Represents the description for an identifier used to specify an order. Used in API response
   * descriptions to provide clarity on the meaning of the order ID field.
   */
  public static final String ORDER_ID = "Order ID";

  /**
   * Represents the description for an identifier used to specify an order item. Used in API
   * response descriptions to provide clarity on the meaning of the order item ID field.
   */
  public static final String ORDER_ITEM_ID = "Order item ID";

  /** Description for the zero-based page index request parameter of paginated endpoints. */
  public static final String PAGE_INDEX =
      "Zero-based page index (negative values are treated as 0)";

  /** Description for the page size request parameter of paginated endpoints. */
  public static final String PAGE_SIZE = "Page size, clamped to the range [1, 100]";

  /**
   * Describes the optional category filter applied when listing products, matched exactly against
   * the category name.
   */
  public static final String PRODUCT_CATEGORY_FILTER = "Exact category name";

  /**
   * Represents the description for an identifier used to specify a product. Used in API response
   * descriptions to provide clarity on the meaning of the product ID field.
   */
  public static final String PRODUCT_ID = "Product ID";

  /**
   * Describes the optional product name filter applied when listing products, matched as a
   * case-insensitive partial match.
   */
  public static final String PRODUCT_NAME_FILTER = "Partial product name (case-insensitive)";

  /**
   * Represents the description for an identifier used to specify a voucher. Used in API response
   * descriptions to provide clarity on the meaning of the voucher ID field.
   */
  public static final String VOUCHER_ID = "Voucher ID";
}
