/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: CommonNotificationType.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-09 16:26:56
 */

package com.nthn.storevision.constant.notification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** Represents the type of notification that is not related to an order. */
@RequiredArgsConstructor
@Getter
public enum CommonNotificationType implements NotificationType {
  /**
   * Rose for an active product whose stock quantity is at or below the configured low-stock
   * threshold (BR-I04), as part of the scheduled low-stock alert job (section 3.2.3).
   */
  LOW_STOCK_ALERT(MessageFormat.LOW_STOCK_MESSAGE_FORMAT, Title.LOW_STOCK_TITLE);
  private final String messageFormat;
  private final String title;

  private enum MessageFormat {
    ;
    private static final String LOW_STOCK_MESSAGE_FORMAT =
        "Product '%s' (SKU %s) has %d unit(s) remaining, at or below the configured low-stock threshold (BR-I04).";
  }

  public enum Title {
    ;
    public static final String LOW_STOCK_TITLE = "Low stock alert";
  }
}
