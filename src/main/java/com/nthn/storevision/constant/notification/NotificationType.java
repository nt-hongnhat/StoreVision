/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: NotificationType.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:51:59
 */

package com.nthn.storevision.constant.notification;

/** Defines the category of an internal {@link com.nthn.storevision.entity.NotificationEntity}. */
public enum NotificationType {
  /**
   * Raised for an active product whose stock quantity is at or below the configured low-stock
   * threshold (BR-I04), as part of the scheduled low-stock alert job (section 3.2.3).
   */
  LOW_STOCK_ALERT
}
