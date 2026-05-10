/*
 * Copyright (c) 2025 Hong Nhat. All rights reserved.
 *
 * Project: Book Thymeleaf
 * Module: Book Thymeleaf Main
 * File: User.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 00:05, 12/04/2025
 */

package com.nthn.storevision.model;

/**
 * Represents a user in the Book Thymeleaf application. This interface provides methods to access
 * and modify user details.
 */
public interface User extends CustomAuditable<Integer> {

  /**
   * Gets the email of the user.
   *
   * @return the email as a {@link String}.
   */
  String getEmail();

  /**
   * Sets the email of the user.
   *
   * @param email the email to set.
   * @return the updated {@code User} instance.
   */
  User setEmail(String email);

  /**
   * Gets the first name of the user.
   *
   * @return the first name as a {@link String}.
   */
  String getFirstName();

  /**
   * Sets the first name of the user.
   *
   * @param firstName the first name to set.
   * @return the updated {@code User} instance.
   */
  User setFirstName(String firstName);

  /**
   * Gets the last name of the user.
   *
   * @return the last name as a {@link String}.
   */
  String getLastName();

  /**
   * Sets the last name of the user.
   *
   * @param lastName the last name to set.
   * @return the updated {@code User} instance.
   */
  User setLastName(String lastName);

  /**
   * Gets the password of the user.
   *
   * @return the password as a {@link String}.
   */
  String getPassword();

  /**
   * Sets the password of the user.
   *
   * @param password the password to set.
   * @return the updated {@code User} instance.
   */
  User setPassword(String password);

  /**
   * Gets the username of the user.
   *
   * @return the username as a {@link String}.
   */
  String getUsername();

  /**
   * Sets the username of the user.
   *
   * @param username the username to set.
   * @return the updated {@code User} instance.
   */
  User setUsername(String username);
}
