/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: Employee.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 21:44:06
 */

package com.nthn.storevision.model;

import com.nthn.storevision.constant.EmployeeRole;

/** Represents an employee (staff member) in the StoreVision domain. */
public interface Employee extends CustomAuditable<Long> {

  /**
   * Gets the hashed password of the employee.
   *
   * @return the hashed password as a {@link String}.
   */
  String getPassword();

  /**
   * Sets the password of the employee. Implementations are expected to hash the value before
   * storing it.
   *
   * @param password the raw password to set.
   * @return the updated {@code Employee} instance.
   */
  Employee setPassword(String password);

  /**
   * Gets the permission role of the employee.
   *
   * @return the {@link EmployeeRole}.
   */
  EmployeeRole getRole();

  /**
   * Sets the permission role of the employee.
   *
   * @param role the {@link EmployeeRole} to set.
   * @return the updated {@code Employee} instance.
   */
  Employee setRole(EmployeeRole role);

  /**
   * Gets the username of the employee used for authentication.
   *
   * @return the username as a {@link String}.
   */
  String getUsername();

  /**
   * Sets the username of the employee.
   *
   * @param username the username to set.
   * @return the updated {@code Employee} instance.
   */
  Employee setUsername(String username);
}
