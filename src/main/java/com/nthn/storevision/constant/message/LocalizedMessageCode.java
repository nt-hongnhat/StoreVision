/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: LocalizedMessageCode.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 21:18:39
 */

package com.nthn.storevision.constant.message;

/**
 * Represents a sealed interface for defining i18n message-code constants within specific domains.
 *
 * <p>This interface serves as the base contract for all message code enumerations used throughout
 * the application. It enforces a consistent structure for defining and retrieving message codes in
 * conjunction with domain-specific enums.
 *
 * <p>Key features of this interface:
 *
 * <ul>
 *   <li>Provides a method for retrieving the actual message code string associated with a constant.
 *   <li>Extends the base behavior of enumerations by allowing additional domain-specific
 *       implementations.
 * </ul>
 *
 * Subtypes:
 *
 * <ul>
 *   <li>{@code AuditMessageCode}: Contains message codes specific to the audit domain.
 *   <li>{@code CustomerMessageCode}: Contains message codes specific to the customer domain.
 *   <li>{@code EmployeeMessageCode}: Contains message codes specific to the employee domain.
 *   <li>{@code InventoryMessageCode}: Contains message codes specific to the inventory domain.
 *   <li>{@code OrderItemMessageCode}: Contains message codes specific to the order item domain.
 *   <li>{@code OrderMessageCode}: Contains message codes specific to the order domain.
 *   <li>{@code ProductMessageCode}: Contains message codes specific to the product domain.
 *   <li>{@code UserMessageCode}: Contains message codes specific to the user domain.
 *   <li>{@code VoucherMessageCode}: Contains message codes specific to the voucher domain.
 * </ul>
 *
 * Implementations of this interface are expected to be enums designed to encapsulate message codes
 * that map to i18n message keys in resource bundles.
 */
public sealed interface LocalizedMessageCode
    permits AuditMessageCode,
        CustomerMessageCode,
        EmployeeMessageCode,
        InventoryMessageCode,
        OrderItemMessageCode,
        OrderMessageCode,
        ProductMessageCode,
        UserMessageCode,
        VoucherMessageCode {

  /**
   * Retrieves the message code string associated with this constant.
   *
   * @return the message code string encapsulated by the constant.
   */
  String getCode();

  /**
   * Retrieves the name of the constant as defined in the corresponding enumeration.
   *
   * @return the name of the constant as a string.
   */
  String name();
}
