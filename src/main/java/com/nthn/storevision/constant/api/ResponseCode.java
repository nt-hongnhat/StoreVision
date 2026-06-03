/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ResponseCode.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 14:12:01
 */

package com.nthn.storevision.constant.api;

/**
 * Compile-time string constants for HTTP response codes, used in SpringDoc
 * {@code @ApiResponse(responseCode)} annotation attributes.
 */
public enum ResponseCode {
  ;

  /** HTTP 400 Bad Request. */
  public static final String BAD_REQUEST = "400";

  /** HTTP 409 Conflict. */
  public static final String CONFLICT = "409";

  /** HTTP 201 Created. */
  public static final String CREATED = "201";

  /** HTTP 403 Forbidden. */
  public static final String FORBIDDEN = "403";

  /** HTTP 404 Not Found. */
  public static final String NOT_FOUND = "404";

  /** HTTP 204 No Content. */
  public static final String NO_CONTENT = "204";

  /** HTTP 200 OK. */
  public static final String OK = "200";

  /** HTTP 401 Unauthorized. */
  public static final String UNAUTHORIZED = "401";
}
