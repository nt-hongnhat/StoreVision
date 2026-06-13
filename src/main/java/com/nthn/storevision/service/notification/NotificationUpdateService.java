/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationUpdateService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 14:54:30
 */

package com.nthn.storevision.service.notification;

import com.nthn.storevision.dto.NotificationDTO;
import org.jspecify.annotations.NonNull;

/** Service interface for updating notification state. */
@FunctionalInterface
public interface NotificationUpdateService {

  /**
   * Marks a notification as read.
   *
   * @param id the notification to update; must not be {@code null}
   * @return the updated notification as a {@link NotificationDTO}; never {@code null}
   * @throws com.nthn.storevision.exception.EntityNotFoundException if the notification does not
   *     exist
   */
  NotificationDTO markAsRead(@NonNull Long id);
}
