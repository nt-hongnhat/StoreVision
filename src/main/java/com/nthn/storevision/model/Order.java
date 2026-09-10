/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: Order.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-10 13:13:37
 */

package com.nthn.storevision.model;

import com.nthn.storevision.constant.order.OrderChannel;
import com.nthn.storevision.constant.order.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/** Represents a customer order in the StoreVision domain. */
public interface Order extends CustomAuditable<Long> {

  /**
   * Gets the sales channel this order originates from.
   *
   * @return the {@link OrderChannel}; never {@code null}.
   */
  OrderChannel getChannel();

  /**
   * Sets the sales channel of this order.
   *
   * @param channel the {@link OrderChannel} to set; must not be {@code null}.
   * @return the updated {@code Order} instance.
   */
  Order setChannel(OrderChannel channel);

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
   * Gets the discount amount granted by the applied voucher.
   *
   * @return the discount amount as a non-negative {@link BigDecimal}; never {@code null}, zero when
   *     no voucher was applied.
   */
  BigDecimal getDiscountAmount();

  /**
   * Sets the discount amount of this order.
   *
   * @param discountAmount the discount amount to set; must not be {@code null}.
   * @return the updated {@code Order} instance.
   */
  Order setDiscountAmount(BigDecimal discountAmount);

  /**
   * Gets the optional note attached to this order.
   *
   * @return the note as a {@link String}, or {@code null} if none was provided.
   */
  String getNote();

  /**
   * Sets an optional note for this order.
   *
   * @param note the note to set; may be {@code null}.
   * @return the updated {@code Order} instance.
   */
  Order setNote(String note);

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
   * Gets the line items belonging to this order.
   *
   * @return the list of {@link OrderItem} instances for this order; never {@code null}, empty when
   *     the order has no items.
   */
  List<? extends OrderItem> getOrderItems();

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
   * Gets the sum of all order item amounts before discount and tax (BR-O03).
   *
   * @return the subtotal as a non-negative {@link BigDecimal}; never {@code null}.
   */
  BigDecimal getSubtotal();

  /**
   * Sets the subtotal of this order.
   *
   * @param subtotal the subtotal to set; must not be {@code null}.
   * @return the updated {@code Order} instance.
   */
  Order setSubtotal(BigDecimal subtotal);

  /**
   * Gets the tax amount applied to this order.
   *
   * @return the tax amount as a non-negative {@link BigDecimal}; never {@code null}.
   */
  BigDecimal getTaxAmount();

  /**
   * Sets the tax amount of this order.
   *
   * @param taxAmount the tax amount to set; must not be {@code null}.
   * @return the updated {@code Order} instance.
   */
  Order setTaxAmount(BigDecimal taxAmount);

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

  /**
   * Gets the voucher applied to this order.
   *
   * @return the applied {@link Voucher}, or {@code null} when no voucher was used.
   */
  Voucher getVoucher();

  /**
   * Sets the voucher applied to this order.
   *
   * @param voucher the {@link Voucher} to apply; may be {@code null} when no voucher is used.
   * @return the updated {@code Order} instance.
   */
  Order setVoucher(Voucher voucher);
}
