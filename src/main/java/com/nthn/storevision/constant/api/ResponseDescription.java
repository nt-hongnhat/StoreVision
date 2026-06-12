/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ResponseDescription.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 16:08:46
 */

package com.nthn.storevision.constant.api;

/**
 * Compile-time string constants for HTTP response descriptions, used in SpringDoc
 * {@code @ApiResponse(description)} annotation attributes.
 */
public enum ResponseDescription {
  ;

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
   * Represents the description for an identifier used to specify an employee. Used in API response
   * descriptions to provide clarity on the meaning of the employee ID field.
   */
  public static final String EMPLOYEE_ID = "Employee ID";

  /** Request body failed validation. */
  public static final String INVALID_INPUT = "Invalid input data";

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
}
