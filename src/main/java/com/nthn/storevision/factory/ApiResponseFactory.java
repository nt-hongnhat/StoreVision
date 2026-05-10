/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ApiResponseFactory.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:09:13
 */

package com.nthn.storevision.factory;

import com.nthn.storevision.dto.response.ApiResponse;

/** Functional interface for creating API responses. */
@FunctionalInterface
public interface ApiResponseFactory {

  /**
   * Creates an {@link ApiResponse} with the specified route and body.
   *
   * @param route the route associated with the API response
   * @param body the body of the API response
   * @return an {@link ApiResponse} object containing the route and body
   */
  ApiResponse create(String route, Object body);
}
