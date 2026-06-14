/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OperationDescription.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 12:34:02
 */

package com.nthn.storevision.constant.api;

/**
 * Defines constants that represent detailed descriptions of various operational processes within
 * the application. Each constant provides a textual representation of a specific operation's
 * functionality and associated business rules.
 */
public enum OperationDescription {
  ;

  /**
   * Immutable constant representing the detailed description of the operation for creating a new
   * sales order.
   *
   * <p>The operation performs the following:
   *
   * <ul>
   *   <li>Validates the customer information and the availability of products in stock without
   *       deducting the inventory (BR-O01).
   *   <li>Validates the optional voucher using business rules (BR-V01, BR-V02, BR-V03, BR-V04).
   *   <li>Computes the subtotal, discounts, taxes, and final total on the server side (BR-O03).
   *   <li>Locks the unit price of each product in the order at the time of creation (BR-O04).
   *   <li>Ensures that the order contains at least one item (BR-O05).
   * </ul>
   *
   * The created order will initially have a status of PENDING.
   */
  public static final String CREATE_ORDER =
      "Creates a sales order in PENDING status. Validates the customer, the products and the stock availability without deducting it (BR-O01), validates the optional voucher (BR-V01–BR-V04), computes subtotal, discount, tax and total server-side (BR-O03) and locks each item's unit price at creation time (BR-O04). The order must contain at least one item (BR-O05).";

  /**
   * Defines a constant representing the operation to mark a customer as deleted without physically
   * removing their record from the database. This allows for the potential restoration of the
   * customer's record in the future.
   *
   * <p>Constraints:
   *
   * <ul>
   *   <li>This operation will be rejected if the customer has any PENDING orders, indicated by
   *       business rule code BR-C02.
   * </ul>
   */
  public static final String CUSTOMER_DELETION =
      "Marks the customer as deleted without physically removing the record. The record can be restored later. Rejected while the customer still has PENDING orders (BR-C02).";

  /**
   * Represents an operation where an employee's record is marked as deleted without permanently
   * removing it from the database. This allows for the record to be restored if needed at a later
   * time.
   */
  public static final String EMPLOYEE_DELETION =
      "Marks the employee as deleted without physically removing the record. The record can be restored later.";

  /**
   * Represents the notification string used for internal low stock alert notifications. This
   * constant is typically used for communications triggered by scheduled jobs (e.g., as described
   * in section 3.2.3 of the documentation). Notifications are ordered with the most recently
   * created ones appearing first.
   */
  public static final String LOW_STOCK_ALERT_NOTIFICATION =
      "Internal notifications (e.g. LOW_STOCK_ALERT raised by the scheduled job, section 3.2.3),"
          + " ordered by most recently created first.";

  /**
   * Represents the customer's loyalty information, including their loyalty tier, point balance,
   * total spending, and an immutable history of point transactions. This string can be used to
   * convey customer loyalty details in a response.
   */
  public static final String LOYALTY =
      "Returns the customer's loyalty tier, point balance, total spending and the immutable point transaction history (BR-C05).";

  /**
   * Description for the operation that marks a specific notification as read. This constant is used
   * to represent the request wherein the `read` field of the notification entity must be set to
   * `true` in the request body.
   */
  public static final String READ_NOTIFICATION_DESCRIPTION =
      "Sets the read flag of the specified notification to true. The request body's `read` field must be `true`.";

  /**
   * Represents the operation for redeeming loyalty points to issue a single-use voucher with a
   * fixed monetary value. The redemption process adheres to the following business rules:
   *
   * <ul>
   *   <li>BR-C03: Redemption is only allowed if there are enough loyalty points in the customer's
   *       balance to cover the voucher's value. The points balance cannot fall below zero.
   *   <li>BR-C04: The redemption does not affect the customer's tier or total spending metrics.
   * </ul>
   */
  public static final String REDEEM =
      "Redeems loyalty points for a single-use FIXED_AMOUNT voucher. The redemption is rejected when it exceeds the current point balance — the balance can never go negative (BR-C03). Tier and total spending are not affected (BR-C04).";

  /**
   * Represents a description for establishing a Server-Sent Events (SSE) connection. This constant
   * is typically used to define the purpose of the connection, which is to receive real-time
   * notification updates from the server.
   */
  public static final String SSE_CONNECTION_DESCRIPTION =
      "Establishes an SSE connection to receive real-time notification updates.";

  /**
   * Represents the process of transitioning an order from PENDING status to either COMPLETED or
   * CANCELLED, abiding by business rules (BR-O02). Both states are considered final.
   *
   * <p>COMPLETED:
   *
   * <ul>
   *   <li>Re-checks stock availability against the order (BR-O01).
   *   <li>Deducts stock quantities and records ISSUE stock movements as part of a single
   *       transaction (BR-I05).
   *   <li>Awards loyalty points to the customer.
   *   <li>Increments the usage count of the applied voucher.
   *   <li>Note: Only staff members are allowed to set an order to COMPLETED.
   * </ul>
   *
   * CANCELLED:
   *
   * <ul>
   *   <li>No stock quantities are deducted.
   *   <li>No loyalty points are awarded.
   * </ul>
   */
  public static final String UPDATE_STATUS =
      "Transitions a PENDING order to COMPLETED or CANCELLED (BR-O02); both are final states. COMPLETED: re-checks stock availability (BR-O01), deducts stock and records ISSUE stock movements in the same transaction (BR-I05), awards loyalty points to the customer and increments the applied voucher's usage count. CANCELLED: no stock is deducted and no loyalty points are awarded. STAFF may only set COMPLETED.";
}
