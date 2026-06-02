/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ApiErrorResponseFactory.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 21:34:51
 */

package com.nthn.storevision.factory;

import com.nthn.storevision.dto.response.ApiErrorResponse;
import com.nthn.storevision.dto.response.ApiResponse;
import com.nthn.storevision.dto.response.Error;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Factory for creating API error responses. Implements the {@link ApiResponseFactory} interface to
 * generate {@link ApiErrorResponse} objects.
 */
@Slf4j
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
    log.debug("Building error response for route: {}", route);

    if (body instanceof final Error error) {
      final ApiResponse response = new ApiErrorResponse(route, error);
      log.debug("Error response built successfully for route: {}", route);
      return response;
    }

    log.warn(
        "Unexpected body type for error response — expected Error but received: {}",
        Optional.ofNullable(body).map(o -> o.getClass().getSimpleName()).orElse("null"));
    throw new IllegalArgumentException("Body must be an instance of Error");
  }
}
