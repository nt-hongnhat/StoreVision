/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: Notification.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:51:59
 */

package com.nthn.storevision.model;

import com.nthn.storevision.constant.notification.NotificationType;

/**
 * Represents an internal system notification in the StoreVision domain. Notifications are created
 * by background jobs (e.g. the low-stock alert scheduler, section 3.2.3) and surfaced to ADMIN and
 * MANAGER users via {@code GET /notifications}.
 */
public interface Notification extends CustomAuditable<Long> {

  /**
   * Gets the human-readable message body of this notification.
   *
   * @return the message as a {@link String}; never {@code null}.
   */
  String getMessage();

  /**
   * Sets the message body of this notification.
   *
   * @param message the message to set; must not be {@code null}.
   * @return the updated {@code Notification} instance.
   */
  Notification setMessage(String message);

  /**
   * Gets the identifier of the entity this notification refers to (e.g. a product id for {@link
   * NotificationType#LOW_STOCK_ALERT}).
   *
   * @return the reference id, or {@code null} when this notification has no related entity.
   */
  Long getReferenceId();

  /**
   * Sets the identifier of the entity this notification refers to.
   *
   * @param referenceId the reference id to set; may be {@code null} when no entity applies.
   * @return the updated {@code Notification} instance.
   */
  Notification setReferenceId(Long referenceId);

  /**
   * Gets the short title of this notification.
   *
   * @return the title as a {@link String}; never {@code null}.
   */
  String getTitle();

  /**
   * Sets the title of this notification.
   *
   * @param title the title to set; must not be {@code null}.
   * @return the updated {@code Notification} instance.
   */
  Notification setTitle(String title);

  /**
   * Gets the category of this notification.
   *
   * @return the {@link NotificationType}; never {@code null}.
   */
  NotificationType getType();

  /**
   * Sets the category of this notification.
   *
   * @param type the {@link NotificationType} to set; must not be {@code null}.
   * @return the updated {@code Notification} instance.
   */
  Notification setType(NotificationType type);

  /**
   * Indicates whether this notification has been read by its recipient.
   *
   * @return {@code true} if the notification has been read; {@code false} otherwise.
   */
  boolean isRead();

  /**
   * Sets the read flag of this notification.
   *
   * @param read {@code true} to mark the notification as read; {@code false} to mark it unread.
   * @return the updated {@code Notification} instance.
   */
  Notification setRead(boolean read);
}
