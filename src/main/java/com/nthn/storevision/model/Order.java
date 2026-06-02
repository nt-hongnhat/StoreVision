/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: Order.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 21:44:02
 */

package com.nthn.storevision.model;

import com.nthn.storevision.constant.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Represents a customer order in the StoreVision domain. */
public interface Order extends CustomAuditable<Long> {

  /**
   * Gets the customer who placed this order.
   *
   * @return the {@link Customer} associated with this order.
   */
  Customer getCustomer();

  /**
   * Sets the customer who placed this order.
   *
   * @param customer the customer to associate.
   * @return the updated {@code Order} instance.
   */
  Order setCustomer(Customer customer);

  /**
   * Gets the date and time when the order was placed.
   *
   * @return the order date as a {@link LocalDateTime}.
   */
  LocalDateTime getOrderDate();

  /**
   * Sets the date and time when the order was placed.
   *
   * @param orderDate the order date to set.
   * @return the updated {@code Order} instance.
   */
  Order setOrderDate(LocalDateTime orderDate);

  /**
   * Gets the current lifecycle status of the order.
   *
   * @return the {@link OrderStatus}.
   */
  OrderStatus getStatus();

  /**
   * Sets the lifecycle status of the order.
   *
   * @param status the {@link OrderStatus} to set.
   * @return the updated {@code Order} instance.
   */
  Order setStatus(OrderStatus status);

  /**
   * Gets the total monetary amount of the order.
   *
   * @return the total amount as a {@link BigDecimal}.
   */
  BigDecimal getTotalAmount();

  /**
   * Sets the total monetary amount of the order.
   *
   * @param totalAmount the total amount to set.
   * @return the updated {@code Order} instance.
   */
  Order setTotalAmount(BigDecimal totalAmount);
}
