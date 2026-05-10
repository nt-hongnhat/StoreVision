/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ApiSuccessResponseFactory.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:08:49
 */

package com.nthn.storevision.factory;

import com.nthn.bookthymeleaf.dto.api_response.ApiResponse;
import com.nthn.bookthymeleaf.dto.api_response.ApiSuccessResponse;
import com.nthn.bookthymeleaf.dto.api_response.Error;
import org.springframework.stereotype.Component;

/**
 * Factory for creating API success responses. Implements the {@link ApiResponseFactory} interface
 * to generate {@link ApiSuccessResponse} objects.
 */
@Component
public class ApiSuccessResponseFactory implements ApiResponseFactory {

  /**
   * Creates an {@link ApiSuccessResponse} with the specified route and body.
   *
   * @param route the route associated with the API success response
   * @param body the body of the API success response, which mustn't be an instance of Error
   * @return an {@link ApiSuccessResponse} object containing the route and response body
   * @throws IllegalArgumentException if the body is an instance of {@link Error}
   */
  @Override
  public ApiResponse create(final String route, final Object body) {
    if (body instanceof Error) {
      throw new IllegalArgumentException("Body must not be an instance of Error");
    }

    return new ApiSuccessResponse<>(route, body);
  }
}
