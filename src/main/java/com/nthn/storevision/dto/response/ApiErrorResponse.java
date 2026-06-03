/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ApiErrorResponse.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:09:13
 */

package com.nthn.storevision.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents an error response for the API. This class extends the {@link ApiResponse} class and
 * includes additional information about the error that occurred.
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ApiErrorResponse extends ApiResponse {

  /** The error details associated with the API response. */
  private final Error error;

  /**
   * Constructs a new {@code ApiErrorResponse} with the specified route and error details.
   *
   * @param route the route associated with the error response
   * @param error the error details
   */
  public ApiErrorResponse(final String route, final Error error) {

    super(route);
    this.error = error;
  }
}
