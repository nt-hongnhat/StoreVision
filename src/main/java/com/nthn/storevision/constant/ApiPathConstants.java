/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ApiPathConstants.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 12:04:18
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
   * Represents the API endpoint path for customer-related operations. This constant is typically
   * used to define routes in the application's API for managing customer data and interactions with
   * the system.
   *
   * <p>Example usage may include operations such as retrieving, creating, updating, or deleting
   * customer records within the application.
   */
  CUSTOMERS(Constants.CUSTOMERS),
  /**
   * Represents the API endpoint pattern for accessing all customer-related resources. This constant
   * is used to define and match request mappings for customer operations (e.g., retrieving customer
   * data, updating customer information) in the application's API routing configuration.
   */
  CUSTOMERS_ALL(Constants.CUSTOMERS_ALL),
  /**
   * Represents the endpoint path for handling all login-related operations and subpaths in the
   * application. This constant is used to define routes associated with login functionality,
   * including any nested paths under the primary login route.
   */
  LOGIN_ALL("/login/**"),
  /**
   * Represents the endpoint path for accessing primary order-related services in the application.
   * This constant is used to define the base route associated with order functionality, excluding
   * nested subpaths or additional operations.
   */
  ORDERS(Constants.ORDERS),
  /**
   * Represents the endpoint path for handling all order-related operations and subpaths in the
   * application. This constant is used to define routes associated with order functionality,
   * including all nested paths under the primary orders route.
   */
  ORDERS_ALL(Constants.ORDERS_ALL),
  /**
   * Represents the API endpoint for accessing and managing individual order items. This constant is
   * used to define the path segment in RESTful service URLs related to order items.
   */
  ORDER_ITEMS(Constants.ORDER_ITEMS),
  /**
   * Represents the API path pattern used to match all order items-related endpoints. This constant
   * serves as a wildcard for any request that involves order item resources, allowing for the
   * processing of operations such as listing, retrieving, updating, or deleting all or specific
   * order items in the system.
   */
  ORDER_ITEMS_ALL(Constants.ORDER_ITEMS_ALL),
  /**
   * Represents the endpoint path for accessing all public-facing resources in the application. This
   * constant is used to define routes associated with publicly accessible services or resources
   * that do not require authentication or authorization.
   */
  PUBLIC_ALL(Constants.PUBLIC_ALL),
  /**
   * Represents the endpoint path for accessing the Swagger UI in the application. This constant is
   * used to define routes associated with the Swagger user interface, which provides a web-based
   * visualization of the API documentation and allows developers to interact with the application's
   * endpoints in a user-friendly manner.
   */
  SWAGGER_UI("/swagger-ui/**");

  private final String value;

  public static class Constants {
    public static final String CUSTOMERS = "/customers";
    public static final String CUSTOMERS_ALL = "/customers/**";
    public static final String ORDERS = "/orders";
    public static final String ORDERS_ALL = "/orders/**";
    public static final String ORDER_ITEMS = "/order-items";
    public static final String ORDER_ITEMS_ALL = "/order-items/**";
    public static final String PUBLIC_ALL = "/public/**";
  }
}
