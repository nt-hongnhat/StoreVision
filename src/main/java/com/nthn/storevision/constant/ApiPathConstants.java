/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ApiPathConstants.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 21:15:26
 */

package com.nthn.storevision.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines constants representing API endpoint paths used in the application. This class helps
 * centralize and manage the path strings for various endpoints, ensuring consistency throughout the
 * application.
 *
 * <p>Features:
 *
 * <ul>
 *   <li>Provides a centralized location for API endpoint paths.
 *   <li>Encapsulates endpoint paths with Lombok's {@code @Getter} and
 *       {@code @RequiredArgsConstructor} for immutability and convenience.
 * </ul>
 */
@RequiredArgsConstructor
@Getter
public enum ApiPathConstants {
  /**
   * Represents the endpoint path for accessing actuator-related services in the application. This
   * constant is used to define routes associated with Spring Boot Actuator endpoints, which provide
   * operational information about the running application.
   */
  ACTUATOR("/actuator/**"),
  /**
   * Represents the endpoint path for accessing API-related services in the application. This
   * constant is used to define routes associated with application-specific API endpoints that
   * handle various business logic and functionalities.
   */
  API("/api/**"),
  /**
   * Represents the endpoint path for accessing API documentation in the application. This constant
   * is used to define routes associated with the OpenAPI or Swagger specification documentation,
   * allowing developers to explore and understand the application's API structure.
   */
  API_DOCS("/v3/api-docs/**"),
  /**
   * Represents the endpoint path for handling login functionality in the application. This constant
   * is used to define routes associated with user authentication or login-related operations.
   */
  LOGIN("/login"),
  /**
   * Represents the endpoint path for handling all login-related operations and subpaths in the
   * application. This constant is used to define routes associated with login functionality,
   * including any nested paths under the primary login route.
   */
  LOGIN_ALL("/login/**"),
  /**
   * Represents the endpoint path for accessing all public-facing resources in the application. This
   * constant is used to define routes associated with publicly accessible services or resources
   * that do not require authentication or authorization.
   */
  PUBLIC_ALL("/public/**"),
  /**
   * Represents the endpoint path for accessing the Swagger UI in the application. This constant is
   * used to define routes associated with the Swagger user interface, which provides a web-based
   * visualization of the API documentation and allows developers to interact with the application's
   * endpoints in a user-friendly manner.
   */
  SWAGGER_UI("/swagger-ui/**");

  private final String value;
}
