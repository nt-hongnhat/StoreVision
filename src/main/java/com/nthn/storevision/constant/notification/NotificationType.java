/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: NotificationType.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-09 16:07:24
 */

package com.nthn.storevision.constant.notification;

/** Defines the category of an internal {@link com.nthn.storevision.entity.NotificationEntity}. */
public sealed interface NotificationType permits CommonNotificationType, OrderNotificationType {

  /**
   * Retrieves the message format associated with this notification type.
   *
   * @return a string representing the message format defined for the notification type.
   */
  String getMessageFormat();

  /**
   * Retrieves the title associated with this notification type.
   *
   * @return a string representing the title of the notification type.
   */
  String getTitle();
}
