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
}
