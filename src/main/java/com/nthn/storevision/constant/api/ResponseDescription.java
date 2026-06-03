/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ResponseDescription.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 14:11:04
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

  /** Resource deleted (soft-deleted) successfully. */
  public static final String DELETED = "Resource deleted successfully";

  /** Request body failed validation. */
  public static final String INVALID_INPUT = "Invalid input data";

  /** The requested resource does not exist. */
  public static final String NOT_FOUND = "Resource not found";

  /** Operation completed successfully. */
  public static final String OK = "Operation completed successfully";
}
