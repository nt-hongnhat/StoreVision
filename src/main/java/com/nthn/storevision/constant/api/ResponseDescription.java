/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ResponseDescription.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 15:36:12
 */

package com.nthn.storevision.constant.api;

/**
 * Compile-time string constants for HTTP response descriptions, used in SpringDoc
 * {@code @ApiResponse(description)} annotation attributes.
 */
public enum ResponseDescription {
  ;

  /**
   * Indicates that the requested reporting date range is invalid because the 'from' date is after
   * the 'to' date (BR-A02).
   *
   * <p>Associated with HTTP response code 400 (Bad Request).
   */
  public static final String ANALYTICS_INVALID_RANGE =
      "The 'from' date is after the 'to' date (BR-A02)";

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

  /** Request body failed validation. */
  public static final String INVALID_INPUT = "Invalid input data";

  /**
   * Indicates that the operation is invalid because the order is not in the PENDING status.
   * Completed and canceled orders are considered final and cannot be further modified or
   * transitioned (BR-O02).
   */
  public static final String INVALID_STATE =
      "Order is not in PENDING status — completed and cancelled orders are final (BR-O02)";

  /** Stock adjustment recorded successfully. */
  public static final String INVENTORY_ADJUSTMENT_RECORDED =
      "Stock adjustment recorded successfully";

  /**
   * Describes the lowStock filter flag, which restricts results to products at or below the
   * configured low-stock threshold (BR-I04).
   */
  public static final String INVENTORY_LOW_STOCK =
      "When true, returns only products with stock quantity at or below the configured low-stock threshold (default 10, BR-I04)";

  /** Stock receipt recorded successfully. */
  public static final String INVENTORY_RECEIPT_CREATED = "Stock receipt recorded successfully";

  /** Describes the inclusive start of the date range used to filter stock receipt history. */
  public static final String INVENTORY_RECEIPT_FROM_DATE =
      "Start of date range (ISO-8601 datetime), inclusive";

  /** Describes the inclusive end of the date range used to filter stock receipt history. */
  public static final String INVENTORY_RECEIPT_TO_DATE =
      "End of date range (ISO-8601 datetime), inclusive";

  /** The requested resource does not exist. */
  public static final String NOT_FOUND = "Resource not found";

  /** Operation completed successfully. */
  public static final String OK = "Operation completed successfully";

  /**
   * Indicates that a product with the same name already exists in the specified category. Used in
   * API response descriptions to notify the client about naming conflicts within the context of
   * product categorization.
   */
  public static final String PRODUCT_NAME_EXISTS_IN_CATEGORY =
      "Product name already exists in the same category";

  /** Resource restored (soft-delete markers cleared) successfully. */
  public static final String RESTORED = "Resource restored successfully";

  /** Indicates that a Server-Sent Events (SSE) connection has been successfully established. */
  public static final String SSE_CONNECTION_ESTABLISHED = "SSE connection established";

  /**
   * Indicates that the request cannot be processed due to insufficient stock quantity (BR-O01) or
   * because the voucher cannot be applied due to one of the following scenarios: BR-V01 to BR-V04.
   */
  public static final String UNPROCESSABLE =
      "Stock quantity is insufficient (BR-O01) or the voucher cannot be applied (BR-V01–BR-V04)";

}
