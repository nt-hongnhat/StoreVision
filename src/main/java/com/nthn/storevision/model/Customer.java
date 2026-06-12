/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: Customer.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02
 */

package com.nthn.storevision.model;

import com.nthn.storevision.constant.LoyaltyTier;
import java.math.BigDecimal;
import java.time.LocalDate;

/** Represents a customer in the StoreVision domain. */
public interface Customer extends CustomAuditable<Long> {

  /**
   * Gets the shipping address of the customer.
   *
   * @return the address as a {@link String}, or {@code null} if not provided.
   */
  String getAddress();

  /**
   * Sets the shipping address of the customer.
   *
   * @param address the address to set.
   * @return the updated {@code Customer} instance.
   */
  Customer setAddress(String address);

  /**
   * Gets the date of birth of the customer, used for birthday benefits.
   *
   * @return the date of birth as a {@link LocalDate}, or {@code null} if not provided.
   */
  LocalDate getDateOfBirth();

  /**
   * Sets the date of birth of the customer.
   *
   * @param dateOfBirth the date of birth to set; may be {@code null}.
   * @return the updated {@code Customer} instance.
   */
  Customer setDateOfBirth(LocalDate dateOfBirth);

  /**
   * Gets the email address of the customer.
   *
   * @return the email as a {@link String}.
   */
  String getEmail();

  /**
   * Sets the email address of the customer.
   *
   * @param email the email to set.
   * @return the updated {@code Customer} instance.
   */
  Customer setEmail(String email);

  /**
   * Gets the unique identifier of the customer.
   *
   * @return the id as a {@link Long}.
   */
  Long getId();

  /**
   * Gets the customer's current loyalty point balance. Never negative (BR-C03).
   *
   * @return the loyalty points as a non-negative {@link Long}; never {@code null}.
   */
  Long getLoyaltyPoints();

  /**
   * Sets the customer's loyalty point balance.
   *
   * @param loyaltyPoints the balance to set; must not be {@code null} and must be non-negative
   *     (BR-C03).
   * @return the updated {@code Customer} instance.
   */
  Customer setLoyaltyPoints(Long loyaltyPoints);

  /**
   * Gets the customer's current loyalty tier, derived from total spending (BR-C04).
   *
   * @return the {@link LoyaltyTier}; never {@code null}.
   */
  LoyaltyTier getLoyaltyTier();

  /**
   * Sets the customer's loyalty tier.
   *
   * @param loyaltyTier the {@link LoyaltyTier} to set; must not be {@code null}.
   * @return the updated {@code Customer} instance.
   */
  Customer setLoyaltyTier(LoyaltyTier loyaltyTier);

  /**
   * Gets the full name of the customer.
   *
   * @return the name as a {@link String}.
   */
  String getName();

  /**
   * Sets the full name of the customer.
   *
   * @param name the name to set.
   * @return the updated {@code Customer} instance.
   */
  Customer setName(String name);

  /**
   * Gets the phone number of the customer.
   *
   * @return the phone number as a {@link String}, or {@code null} if not provided.
   */
  String getPhone();

  /**
   * Sets the phone number of the customer.
   *
   * @param phone the phone number to set.
   * @return the updated {@code Customer} instance.
   */
  Customer setPhone(String phone);

  /**
   * Gets the customer's accumulated total spending; never resets (BR-C04).
   *
   * @return the total spending as a non-negative {@link BigDecimal}; never {@code null}.
   */
  BigDecimal getTotalSpending();

  /**
   * Sets the customer's accumulated total spending.
   *
   * @param totalSpending the total spending to set; must not be {@code null} and must be
   *     non-negative.
   * @return the updated {@code Customer} instance.
   */
  Customer setTotalSpending(BigDecimal totalSpending);
}
