/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ApiSuccessResponseFactory.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 21:36:26
 */

package com.nthn.storevision.factory;

import com.nthn.storevision.dto.response.ApiResponse;
import com.nthn.storevision.dto.response.ApiSuccessResponse;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Factory for creating API success responses. Implements the {@link ApiResponseFactory} interface
 * to generate {@link com.nthn.storevision.dto.response.ApiSuccessResponse} objects.
 */
@Slf4j
@Component
public class ApiSuccessResponseFactory implements ApiResponseFactory {

  /**
   * Creates an {@link com.nthn.storevision.dto.response.ApiSuccessResponse} with the specified
   * route and body.
   *
   * @param route the route associated with the API success response
   * @param body the body of the API success response, which mustn't be an instance of Error
   * @return an {@link com.nthn.storevision.dto.response.ApiSuccessResponse} object containing the
   *     route and response body
   * @throws IllegalArgumentException if the body is an instance of {@link Error}
   */
  @Override
  public ApiResponse create(final String route, final Object body) {
    log.debug("Building success response for route: {}", route);

    if (body instanceof final Error error) {
      log.warn(
          "Invalid body type for success response — received Error type: {}",
          Optional.of(error).map(o -> o.getClass().getSimpleName()).orElse("null"));
      throw new IllegalArgumentException("Body must not be an instance of Error");
    }

    final ApiResponse response = new ApiSuccessResponse<>(route, body);
    log.debug("Success response built successfully for route: {}", route);
    return response;
  }
}
