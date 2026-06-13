/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationController.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 17:35:32
 */

package com.nthn.storevision.controller;

import com.nthn.storevision.dto.NotificationDTO;
import com.nthn.storevision.dto.response.PageDTO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * Version-agnostic contract for internal notification endpoints.
 *
 * @see com.nthn.storevision.controller.v1.NotificationControllerV1
 */
public interface NotificationController {

  /**
   * Returns one page of notifications, most recently created first.
   *
   * @param page zero-based page index; negative values are treated as 0
   * @param size page size; clamped to the range [1, 100]
   * @return the requested page of notifications; never {@code null}
   * @see com.nthn.storevision.controller.v1.NotificationControllerV1#getNotifications
   */
  PageDTO<NotificationDTO> getNotifications(int page, int size);

  /**
   * Marks a notification as read.
   *
   * @param id the notification id to update; must not be {@code null}
   * @return the updated notification
   * @see com.nthn.storevision.controller.v1.NotificationControllerV1#markNotificationAsRead
   */
  NotificationDTO markNotificationAsRead(Long id);

  /**
   * Establishes an SSE connection for real-time notification updates.
   *
   * @return An SseEmitter instance.
   */
  SseEmitter streamNotifications();
}
