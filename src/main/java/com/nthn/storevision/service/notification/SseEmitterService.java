/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: SseEmitterService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:56:17
 */

package com.nthn.storevision.service.notification;

import com.nthn.storevision.dto.notification.NotificationDTO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/** Service for managing Server-Sent Events (SSE) emitters and sending real-time notifications. */
public interface SseEmitterService {

  /**
   * Adds a new {@link SseEmitter} to the list of managed emitters. Sets up completion and timeout
   * callbacks to remove the emitter.
   *
   * @param emitter The {@link SseEmitter} to add.
   */
  void addEmitter(SseEmitter emitter);

  /**
   * Sends a notification to all registered SseEmitters. Emitters that fail to send are removed from
   * the list.
   *
   * @param notification The {@link NotificationDTO} to send.
   */
  void sendNotification(NotificationDTO notification);
}
