/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ResponseDescription.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 12:27:34
 */

package com.nthn.storevision.constant.api;

/**
 * Compile-time string constants for HTTP response descriptions, used in SpringDoc
 * {@code @ApiResponse(description)} annotation attributes.
 */
public enum ResponseDescription {
  ;

  /**
   * Represents a response description indicating a stock-related inconsistency during order
   * completion.
   *
   * <p>This constant is used to signal that the stock quantity is not enough for one or more order
   * items at the time of order completion. It generally corresponds to scenarios requiring
   * attention to inventory levels before proceeding with the order finalization.
   */
  public static final String COMPLETE_STOCK =
      "Stock quantity is insufficient for one or more order items at completion time (BR-O01)";

  /** Resource created successfully. */
  public static final String CREATED = "Resource created successfully";

  /**
   * Represents the description for an identifier used to specify a customer. Used in API response
   * descriptions to provide clarity on the meaning of the customer ID field.
   */
  public static final String CUSTOMER_ID = "Customer ID";

  /** Resource deleted (soft-deleted) successfully. */
  public static final String DELETED = "Resource deleted successfully";

  /**
   * Indicates that the customer's email address already exists in the system. This includes cases
   * where the email is found in records that have been soft-deleted. Typically used to signal a
   * conflict when attempting operations that require a unique email address, such as customer
   * creation.
   *
   * <p>Associated with HTTP response code 409 (Conflict).
   */
  public static final String EMAIL_EXISTS =
      "Customer email already exists, including on soft-deleted records (BR-C01)";

  /**
   * Represents the description for an identifier used to specify an employee. Used in API response
   * descriptions to provide clarity on the meaning of the employee ID field.
   */
  public static final String EMPLOYEE_ID = "Employee ID";

  /**
   * Indicates whether the operation involves permanently removing a record instead of performing a
   * soft-delete. This action is restricted to admin-level permissions.
   */
  public static final String HARD_DELETE =
      "If true, permanently removes the record instead of soft-deleting it (Admin only)";

  /**
   * Message indicating that a customer cannot be deleted due to having pending orders. Typically
   * used as part of validation or error response descriptions.
   */
  public static final String HAS_PENDING_ORDERS =
      "Customer still has PENDING orders and cannot be deleted (BR-C02)";

  /**
   * Error message indicating that the customer does not have enough points in their balance to
   * fulfill the request. This constant is typically used to represent a response or validation
   * error in scenarios involving loyalty points or rewards systems.
   */
  public static final String INSUFFICIENT_POINTS =
      "Requested points exceed the customer's current balance (BR-C03)";

  /** Request body failed validation. */
  public static final String INVALID_INPUT = "Invalid input data";

  /**
   * Indicates that the operation is invalid because the order is not in the PENDING status.
   * Completed and canceled orders are considered final and cannot be further modified or
   * transitioned (BR-O02).
   */
  public static final String INVALID_STATE =
      "Order is not in PENDING status — completed and cancelled orders are final (BR-O02)";

  /**
   * Represents the description for an identifier used to specify a notification. Used in API
   * response descriptions to provide clarity on the meaning of the notification ID field.
   */
  public static final String NOTIFICATION_ID = "Notification ID";

  /** The requested resource does not exist. */
  public static final String NOT_FOUND = "Resource not found";

  /** Operation completed successfully. */
  public static final String OK = "Operation completed successfully";

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
   * Represents the description for an identifier used to specify a product. Used in API response
   * descriptions to provide clarity on the meaning of the product ID field.
   */
  public static final String PRODUCT_ID = "Product ID";

  /**
   * Indicates that a product with the same name already exists in the specified category. Used in
   * API response descriptions to notify the client about naming conflicts within the context of
   * product categorization.
   */
  public static final String PRODUCT_NAME_EXISTS_IN_CATEGORY =
      "Product name already exists in the same category";

  /** Resource restored (soft-delete markers cleared) successfully. */
  public static final String RESTORED = "Resource restored successfully";

  /**
   * Indicates that the request cannot be processed due to insufficient stock quantity (BR-O01) or
   * because the voucher cannot be applied due to one of the following scenarios: BR-V01 to BR-V04.
   */
  public static final String UNPROCESSABLE =
      "Stock quantity is insufficient (BR-O01) or the voucher cannot be applied (BR-V01–BR-V04)";

  /**
   * Represents the description for an identifier used to specify a voucher. Used in API response
   * descriptions to provide clarity on the meaning of the voucher ID field.
   */
  public static final String VOUCHER_ID = "Voucher ID";
}
