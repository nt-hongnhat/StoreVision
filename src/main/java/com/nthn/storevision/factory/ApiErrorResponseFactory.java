/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ApiErrorResponseFactory.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:08:49
 */

package com.nthn.storevision.factory;

import com.nthn.storevision.dto.response.ApiErrorResponse;
import com.nthn.storevision.dto.response.ApiResponse;
import com.nthn.storevision.dto.response.Error;
import org.springframework.stereotype.Component;

/**
 * Factory for creating API error responses. Implements the {@link ApiResponseFactory} interface to
 * generate {@link ApiErrorResponse} objects.
 */
@Component
public class ApiErrorResponseFactory implements ApiResponseFactory {

  /**
   * Creates an {@link ApiErrorResponse} with the specified route and body.
   *
   * @param route the route associated with the API error response
   * @param body the body of the API error response, which must be an instance of Error
   * @return an {@link ApiErrorResponse} object containing the route and error details
   * @throws IllegalArgumentException if the body isn't an instance of {@link Error}
   */
  @Override
  public ApiResponse create(final String route, final Object body) {
    if (body instanceof final Error error) {
      return new ApiErrorResponse(route, error);
    }

    throw new IllegalArgumentException("Body must be an instance of Error");
  }
}
