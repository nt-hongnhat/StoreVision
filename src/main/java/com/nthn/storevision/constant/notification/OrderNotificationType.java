/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: OrderNotificationType.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-09 16:21:55
 */

package com.nthn.storevision.constant.notification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** Represents the type of notification related to an order. */
@RequiredArgsConstructor
@Getter
public enum OrderNotificationType implements NotificationType {
  /** Rose for an order that was canceled because an item is out of stock (order workflow). */
  ORDER_OUT_OF_STOCK(
      MessageFormat.ORDER_OUT_OF_STOCK_MESSAGE_FORMAT, Title.ORDER_OUT_OF_STOCK_TITLE),
  /** Rose for an order that was canceled because payment confirmation failed (order workflow). */
  ORDER_PAYMENT_FAILED(
      MessageFormat.ORDER_PAYMENT_FAILED_MESSAGE_FORMAT, Title.ORDER_PAYMENT_FAILED_TITLE),
  /** Rose for an order whose payment confirmation succeeded (order workflow). */
  ORDER_COMPLETED(MessageFormat.ORDER_COMPLETED_MESSAGE_FORMAT, Title.ORDER_COMPLETED_TITLE);
  private final String messageFormat;
  private final String title;

  private enum MessageFormat {
    ;
    private static final String ORDER_COMPLETED_MESSAGE_FORMAT =
        "Order #%d has been paid and completed successfully.";
    private static final String ORDER_OUT_OF_STOCK_MESSAGE_FORMAT =
        "Order #%d was cancelled because one or more items are out of stock.";
    private static final String ORDER_PAYMENT_FAILED_MESSAGE_FORMAT =
        "Order #%d was cancelled because payment confirmation failed.";
  }

  private enum Title {
    ;
    private static final String ORDER_COMPLETED_TITLE = "Order completed";
    private static final String ORDER_OUT_OF_STOCK_TITLE = "Order cancelled: out of stock";
    private static final String ORDER_PAYMENT_FAILED_TITLE = "Order cancelled: payment failed";
  }
}
