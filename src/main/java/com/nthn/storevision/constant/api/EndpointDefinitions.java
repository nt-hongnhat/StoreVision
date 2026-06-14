/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EndpointDefinitions.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 15:44:24
 */

package com.nthn.storevision.constant.api;

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
public enum EndpointDefinitions {
  ;

  /**
   * Represents the endpoint path pattern for accessing all actuator-related endpoints.
   *
   * <p>Use this constant to define authorization, routing, or any configurations relying on the
   * actuator endpoints in the application. The wildcard pattern ensures all actuator endpoints,
   * such as health checks or metrics, are included.
   */
  public static final String ACTUATOR_ALL = "/actuator/**";

  /**
   * Represents the endpoint path pattern for accessing analytics-related endpoints.
   *
   * <p>Use this constant to define authorization, routing, or any configurations relying on the
   * analytics endpoints in the application.
   */
  public static final String ANALYTICS = "/analytics";

  /**
   * Represents the base API path pattern used for defining application-wide API endpoint mappings.
   *
   * <p>This constant is used to specify the root path for various API endpoints in the application.
   * It defines a generic pattern that matches all sub-paths under the "/api/" base, commonly used
   * for managing and organizing RESTful API routes.
   *
   * <p>The double asterisk ("**") indicates that the path can match zero or more levels of path
   * segments, making it suitable for use in mapping multiple routes dynamically.
   */
  public static final String API = "/api/**";

  /**
   * Represents the endpoint path pattern for accessing API documentation-related endpoints.
   *
   * <p>Use this constant to define authorization, routing, or any configurations relying on the API
   * documentation endpoints in the application.
   */
  public static final String API_DOCS_ALL = "/v3/api-docs/**";

  /**
   * Represents the endpoint path pattern for accessing customer-related endpoints.
   *
   * <p>Use this constant to define authorization, routing, or any configurations relying on the
   * customer endpoints in the application.
   */
  public static final String CUSTOMERS = "/customers";

  /**
   * Represents the endpoint path pattern for accessing all customer-related endpoints.
   *
   * <p>Use this constant to define authorization, routing, or any configurations relying on the
   * customer endpoints in the application. The wildcard pattern ensures all customer-related
   * endpoints are included.
   */
  public static final String CUSTOMERS_ALL = "/customers/**";

  /**
   * Represents the endpoint path pattern for accessing employee-related endpoints.
   *
   * <p>Use this constant to define authorization, routing, or any configurations relying on the
   * employee endpoints in the application.
   */
  public static final String EMPLOYEES = "/employees";

  /**
   * Represents the endpoint path pattern for accessing inventory-related endpoints.
   *
   * <p>Use this constant to define authorization, routing, or any configurations relying on the
   * inventory endpoints in the application.
   */
  public static final String INVENTORY = "/inventory";

  /**
   * Represents the API endpoint pattern for all login-related requests. This constant is used to
   * define access permissions or routing rules for endpoints under the login namespace.
   *
   * <p>Example usage includes securing or configuring access to any endpoint that matches the
   * specified pattern.
   */
  public static final String LOGIN_ALL = "/login/**";

  /**
   * Represents the API endpoint pattern for all notifications-related requests. This constant is
   * used to define access permissions or routing rules for endpoints under the notifications
   * namespace.
   *
   * <p>Example usage includes securing or configuring access to any endpoint that matches the
   * specified pattern.
   */
  public static final String NOTIFICATIONS = "/notifications";

  /**
   * Represents the API endpoint pattern for all orders-related requests. This constant is used to
   * define access permissions or routing rules for endpoints under the orders namespace.
   *
   * <p>Example usage includes securing or configuring access to any endpoint that matches the
   * specified pattern.
   */
  public static final String ORDERS = "/orders";

  /**
   * Represents the API endpoint pattern for accessing all order-related resources. This constant is
   * part of the path configuration for routing requests to operations associated with orders,
   * including sub-resources or functionalities specified by further path extensions. Matches any
   * request path that starts with "/orders/".
   */
  public static final String ORDERS_ALL = "/orders/**";

  /**
   * Represents the API endpoint path for operations related to order items. This constant is used
   * to define the base URL segment for accessing order item-related resources within the
   * application.
   */
  public static final String ORDER_ITEMS = "/order-items";

  /**
   * Represents the API endpoint pattern for accessing all order items-related resources. This
   * constant is part of the path configuration for routing requests to operations associated with
   * order items, including sub-resources or functionalities specified by further path extensions.
   * Matches any request path that starts with "/order-items/".
   */
  public static final String ORDER_ITEMS_ALL = "/order-items/**";

  /**
   * Represents the API endpoint path for managing products. This constant is commonly used to
   * define and refer to the base URL path of the product-related operations in the application.
   */
  public static final String PRODUCTS = "/products";

  /**
   * Represents a constant that defines the public API path pattern. This pattern allows
   * unrestricted access to all API endpoints under the `/public/**` URL path.
   */
  public static final String PUBLIC_ALL = "/public/**";

  /**
   * Represents the API endpoint for managing receipts in the system.
   *
   * <p>This constant is used in routing configurations, controllers, and other components to define
   * the relative path for accessing receipt-related functionality.
   *
   * <p>It facilitates operations like viewing, creating, updating, or deleting receipt records,
   * depending on the specific API implementation.
   */
  public static final String RECEIPTS = "/receipts";

  /**
   * Represents the API endpoint path for accessing Swagger UI resources. This constant is used to
   * define the base URL segment for accessing Swagger UI-related resources within the application.
   */
  public static final String SWAGGER_UI = "/swagger-ui/**";

  /**
   * Represents the API path for operations related to vouchers in the application. This constant
   * provides a standardized endpoint that can be used throughout the system to access
   * voucher-related resources.
   */
  public static final String VOUCHERS = "/vouchers";
}
