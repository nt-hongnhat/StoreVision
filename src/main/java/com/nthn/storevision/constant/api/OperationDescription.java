/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OperationDescription.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 15:35:20
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
   * Represents the description for the inventory valuation report, which prices the current stock
   * of every active product.
   */
  public static final String ANALYTICS_INVENTORY_VALUATION =
      "Current inventory value: average RECEIPT unit cost times units in stock, per product plus the grand total. Products never received are valued at zero.";

  /**
   * Represents the description for the order summary report, which aggregates order totals per
   * status alongside completed-order revenue (BR-A01) over a date range.
   */
  public static final String ANALYTICS_ORDER_SUMMARY =
      "Order totals per status plus completed-order revenue (BR-A01) for the requested range.";

  /**
   * Represents the description for the gross profit report, computed as completed-order revenue
   * (BR-A01) minus the cost of goods sold over a date range.
   */
  public static final String ANALYTICS_PROFIT =
      "Gross profit over the range: completed-order revenue (BR-A01) minus cost of goods sold, where unit cost is each product's average RECEIPT cost.";

  /**
   * Represents the description for the revenue report, which buckets completed-order revenue
   * (BR-A01) by day, week, or month over a date range.
   */
  public static final String ANALYTICS_REVENUE =
      "Buckets completed-order revenue (BR-A01) by day, week or month over the requested range.";

  /**
   * Represents the description for the slow-moving products report, which ranks active products by
   * the fewest completed sales within a look-back window.
   */
  public static final String ANALYTICS_SLOW_MOVING_PRODUCTS =
      "Active products with the fewest completed sales in the look-back window; products never sold rank first, ties broken by the highest remaining stock.";

  /**
   * Represents the description for the top-selling products report, which ranks products by units
   * sold through completed orders (BR-A01) within a rolling period.
   */
  public static final String ANALYTICS_TOP_SELLING_PRODUCTS =
      "Best-selling products by units sold through completed orders (BR-A01) within the rolling period (today, last 7 days or last 30 days).";

  /**
   * Represents the description for the top-spending customers report, which ranks customers by
   * accumulated lifetime spending (BR-C04).
   */
  public static final String ANALYTICS_TOP_SPENDING_CUSTOMERS =
      "Customers ranked by accumulated lifetime spending (BR-C04).";

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
   * Represents the operation for creating a new customer record, enforcing a system-wide unique
   * email (BR-C01) and initializing the customer at BRONZE loyalty tier with zero points.
   */
  public static final String CUSTOMER_CREATION =
      "Creates a new customer record. The email must be unique across the whole system, including soft-deleted records (BR-C01). New customers start at BRONZE tier with zero points.";

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

  /** Represents the operation for retrieving a single customer record by its identifier. */
  public static final String CUSTOMER_GET_BY_ID =
      "Returns the customer record with the specified ID.";

  /**
   * Represents the operation for listing active (non-deleted) customer records, ordered by name and
   * paginated.
   */
  public static final String CUSTOMER_LIST =
      "Returns one page of active (non-deleted) customer records ordered by name.";

  /**
   * Represents the operation for retrieving the order history of a customer, ordered with the most
   * recently placed order first.
   */
  public static final String CUSTOMER_ORDER_HISTORY =
      "Returns all active orders of the customer, most recent first.";

  /** Represents the operation for updating the mutable fields of an existing customer record. */
  public static final String CUSTOMER_UPDATE =
      "Updates the mutable fields (name, email, phone, address) of an existing customer.";

  /** Represents the operation for creating a new employee record. */
  public static final String EMPLOYEE_CREATION =
      "Creates a new employee record and persists it to the database.";

  /**
   * Represents an operation where an employee's record is marked as deleted without permanently
   * removing it from the database. This allows for the record to be restored if needed at a later
   * time.
   */
  public static final String EMPLOYEE_DELETION =
      "Marks the employee as deleted without physically removing the record. The record can be restored later.";

  /** Represents the operation for retrieving a single employee record by its identifier. */
  public static final String EMPLOYEE_GET_BY_ID =
      "Returns the employee record with the specified ID.";

  /**
   * Represents the operation for listing active (non-deleted) employee records, ordered by username
   * and paginated.
   */
  public static final String EMPLOYEE_LIST =
      "Returns one page of active (non-deleted) employee records ordered by username.";

  /** Represents the operation for updating the mutable fields of an existing employee record. */
  public static final String EMPLOYEE_UPDATE =
      "Updates the mutable fields (username, password, role) of an existing employee.";

  /**
   * Represents the operation for recording a manual stock adjustment, which can either increase or
   * decrease a product's stock quantity.
   */
  public static final String INVENTORY_ADJUSTMENT =
      "Records an ADJUSTMENT stock movement and updates the product's stock quantity. Quantity can be positive (increase) or negative (decrease).";

  /** Represents the operation for retrieving the current stock level of a single product. */
  public static final String INVENTORY_GET_BY_PRODUCT_ID =
      "Returns current stock details for the specified product.";

  /**
   * Represents the operation for listing current stock levels of active products, optionally
   * filtered to only those at or below the configured low-stock threshold (BR-I04).
   */
  public static final String INVENTORY_LIST =
      "Returns current stock quantity and price for active products. When lowStock=true, only products at or below the configured low-stock threshold are returned (BR-I04).";

  /**
   * Represents the operation for listing the stock movement history of a product, ordered with the
   * most recent movement first.
   */
  public static final String INVENTORY_MOVEMENT_HISTORY =
      "All stock movements (RECEIPT, ISSUE, ADJUSTMENT) for the product, ordered by most recent first.";

  /**
   * Represents the operation for recording a stock receipt, which increments a product's stock
   * quantity and enforces BR-I01 (performed_by) and BR-I02 (quantity &gt;= 1).
   */
  public static final String INVENTORY_RECEIPT =
      "Creates a RECEIPT stock movement and increments the product's stock quantity. Enforces BR-I01 (performed_by) and BR-I02 (quantity >= 1).";

  /**
   * Represents the operation for listing the stock receipt history of products, optionally filtered
   * by date range and paginated.
   */
  public static final String INVENTORY_RECEIPT_HISTORY =
      "RECEIPT movements ordered by most recent first. Both date parameters are optional: omit to retrieve full history.";

  /**
   * Represents the notification string used for internal low stock alert notifications. This
   * constant is typically used for communications triggered by scheduled jobs (e.g., as described
   * in section 3.2.3 of the documentation). Notifications are ordered with the most recently
   * created ones appearing first.
   */
  public static final String LOW_STOCK_ALERT_NOTIFICATION =
      "Internal notifications (e.g. LOW_STOCK_ALERT raised by the scheduled job, section 3.2.3), ordered by most recently created first.";

  /**
   * Represents the customer's loyalty information, including their loyalty tier, point balance,
   * total spending, and an immutable history of point transactions. This string can be used to
   * convey customer loyalty details in a response.
   */
  public static final String LOYALTY =
      "Returns the customer's loyalty tier, point balance, total spending and the immutable point transaction history (BR-C05).";

  /**
   * Represents an operation where an order's record is marked as deleted without permanently
   * removing it from the database. This allows for the record to be restored if needed at a later
   * time.
   */
  public static final String ORDER_DELETION =
      "Marks the order as deleted without physically removing the record. The record can be restored later.";

  /** Represents the operation for retrieving a single order record by its identifier. */
  public static final String ORDER_GET_BY_ID = "Returns the order record with the specified ID.";

  /** Represents the operation for adding a new line item to an existing order. */
  public static final String ORDER_ITEM_CREATION =
      "Adds a new line item to an existing order and persists it to the database.";

  /**
   * Represents an operation where an order item's record is marked as deleted without permanently
   * removing it from the database. This allows for the record to be restored if needed at a later
   * time.
   */
  public static final String ORDER_ITEM_DELETION =
      "Marks the order item as deleted without physically removing the record. The record can be restored later.";

  /** Represents the operation for retrieving a single order item record by its identifier. */
  public static final String ORDER_ITEM_GET_BY_ID =
      "Returns the order item record with the specified ID.";

  /**
   * Represents the operation for listing active (non-deleted) order item records, optionally
   * filtered to a single parent order.
   */
  public static final String ORDER_ITEM_LIST =
      "Returns one page of active (non-deleted) order item records, optionally filtered to a single parent order via the orderId query parameter.";

  /**
   * Represents the operation for updating the mutable fields of an existing order item; the order
   * and product associations cannot be changed.
   */
  public static final String ORDER_ITEM_UPDATE =
      "Updates the mutable fields (quantity, unitPrice) of an existing order item. Order and product associations cannot be changed via this endpoint.";

  /**
   * Represents the operation for listing active (non-deleted) order records, ordered with the most
   * recently created order first.
   */
  public static final String ORDER_LIST =
      "Returns one page of active (non-deleted) order records, most recent first.";

  /**
   * Represents the operation for updating the mutable fields of an existing order; the customer
   * association cannot be changed.
   */
  public static final String ORDER_UPDATE =
      "Updates the mutable fields (orderDate, totalAmount, status) of an existing order. Customer association cannot be changed via this endpoint.";

  /**
   * Represents the operation for creating a new product, enforcing a unique name + category
   * combination (BR-P01), a positive price (BR-P02) and a non-negative stock quantity (BR-P03).
   */
  public static final String PRODUCT_CREATION =
      "Creates a new product. Name + category must be unique among active records (BR-P01). Price must be > 0 (BR-P02). stockQuantity must be >= 0 (BR-P03).";

  /**
   * Represents the operation for deleting a product: by default a soft delete that logs a warning
   * when PENDING orders still reference the product (BR-P04), or a permanent removal when {@code
   * hard=true} (admin only).
   */
  public static final String PRODUCT_DELETION =
      "By default, soft-deletes the product. If active PENDING orders reference this product, a warning is logged but the deletion still proceeds (BR-P04). If `hard=true`, permanently removes the record from the database instead (Admin only).";

  /** Represents the operation for retrieving a single product record by its identifier. */
  public static final String PRODUCT_GET_BY_ID =
      "Returns the product record with the specified ID.";

  /**
   * Represents the operation for listing non-deleted product records ordered by name, optionally
   * filtered by name and/or category.
   */
  public static final String PRODUCT_LIST =
      "Returns one page of non-deleted product records ordered by name, optionally filtered by name (case-insensitive, partial match) and/or category (exact match). Omit a filter parameter to skip it.";

  /**
   * Represents the operation for restoring a soft-deleted product by clearing its soft-delete
   * markers.
   */
  public static final String PRODUCT_RESTORE =
      "Clears the soft-delete markers, making the product active again. The request body's `deleted` field must be `false`.";

  /**
   * Represents the operation for updating all mutable fields of an existing product, enforcing the
   * unique name + category combination among active records (BR-P01).
   */
  public static final String PRODUCT_UPDATE =
      "Updates all mutable fields of an existing product. Name + category must remain unique among active records, excluding this product's own record (BR-P01).";

  /**
   * Description for the operation that marks a specific notification as read. This constant is used
   * to represent the request wherein the `read` field of the notification entity must be set to
   * `true` in the request body.
   */
  public static final String READ_NOTIFICATION =
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
  public static final String SSE_CONNECTION =
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

  /** Represents the operation for creating a new promotional voucher. */
  public static final String VOUCHER_CREATION = "Creates a new promotional voucher.";

  /**
   * Represents an operation where a voucher's record is marked as deleted without permanently
   * removing it from the database. This allows for the record to be restored if needed at a later
   * time.
   */
  public static final String VOUCHER_DELETION =
      "Marks the voucher as deleted without physically removing the record. The record can be restored later.";

  /** Represents the operation for retrieving a single voucher record by its identifier. */
  public static final String VOUCHER_GET_BY_ID =
      "Returns the voucher record with the specified ID.";

  /**
   * Represents the operation for listing active (non-deleted) voucher records, ordered with the
   * most recently created voucher first.
   */
  public static final String VOUCHER_LIST =
      "Returns one page of active (non-deleted) voucher records, most recent first.";

  /**
   * Represents the operation for restoring a soft-deleted voucher by clearing its soft-delete
   * markers.
   */
  public static final String VOUCHER_RESTORE =
      "Clears the soft-delete markers, making the voucher active again. The request body's `deleted` field must be `false`.";

  /** Represents the operation for updating an existing voucher. */
  public static final String VOUCHER_UPDATE = "Updates an existing voucher.";
}
