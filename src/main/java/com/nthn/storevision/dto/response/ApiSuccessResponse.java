/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ApiSuccessResponse.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2025-04-20 00:10:57
 */

package com.nthn.storevision.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents a successful response for the API. This class extends the
 * {@link ApiResponse} class and includes the data returned from the API
 * request.
 *
 * @param <T> the type of the data returned in the response
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ApiSuccessResponse<T> extends ApiResponse {

  /**
   * The data returned in the API response.
   */
  private final T data;

  /**
   * Constructs a new {@code ApiSuccessResponse} with the specified route and
   * data.
   *
   * @param route the route associated with the successful response
   * @param data  the data returned from the API request
   */
  public ApiSuccessResponse(final String route, final T data) {

    super(route);
    this.data = data;
  }
}
