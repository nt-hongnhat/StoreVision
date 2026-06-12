/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: AuditMessageCode.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 17:13:03
 */

package com.nthn.storevision.constant.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * i18n message-code constants for the {@code audit} domain. Prefix: {@code AUM}.
 *
 * <p>All codes correspond to entries in {@code /i18n/messages*.properties}.
 */
@Getter
@RequiredArgsConstructor
public enum AuditMessageCode implements LocalizedMessageCode {
  /**
   * Represents the message code for an error where an annotated audit field could not be set.
   *
   * <p>This code is used when reflection fails while populating a field annotated with
   * {@code @DeletedBy} or {@code @DeletedDate} on an entity.
   */
  ANNOTATED_FIELD_SET_FAILED("AUM001"),
  /**
   * Represents the message code for an error where invoking a field's setter method failed.
   *
   * <p>This code is used when the setter exists, but its reflective invocation throws an exception.
   */
  SETTER_INVOCATION_FAILED("AUM003"),
  /**
   * Represents the message code for an error where no setter method exists for a field.
   *
   * <p>This code is used when reflection cannot find a matching setter for an audit field.
   */
  SETTER_NOT_FOUND("AUM002");

  private final String code;
}
