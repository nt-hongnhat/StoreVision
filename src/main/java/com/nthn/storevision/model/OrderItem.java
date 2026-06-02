/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItem.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 21:43:53
 */

package com.nthn.storevision.model;

import java.math.BigDecimal;

/** Represents a single line item within a customer order in the StoreVision domain. */
public interface OrderItem extends CustomAuditable<Long> {

  /**
   * Gets the order this item belongs to.
   *
   * @return the parent {@link Order}.
   */
  Order getOrder();

  /**
   * Sets the order this item belongs to.
   *
   * @param order the parent order to associate.
   * @return the updated {@code OrderItem} instance.
   */
  OrderItem setOrder(Order order);

  /**
   * Gets the product referenced by this order item.
   *
   * @return the {@link Product} for this line item.
   */
  Product getProduct();

  /**
   * Sets the product referenced by this order item.
   *
   * @param product the product to associate.
   * @return the updated {@code OrderItem} instance.
   */
  OrderItem setProduct(Product product);

  /**
   * Gets the quantity of the product ordered.
   *
   * @return the quantity as an {@link Integer}.
   */
  Integer getQuantity();

  /**
   * Sets the quantity of the product ordered.
   *
   * @param quantity the quantity to set.
   * @return the updated {@code OrderItem} instance.
   */
  OrderItem setQuantity(Integer quantity);

  /**
   * Gets the unit price of the product at the time of the order.
   *
   * @return the unit price as a {@link BigDecimal}.
   */
  BigDecimal getUnitPrice();

  /**
   * Sets the unit price of the product at the time of the order.
   *
   * @param unitPrice the unit price to set.
   * @return the updated {@code OrderItem} instance.
   */
  OrderItem setUnitPrice(BigDecimal unitPrice);
}
