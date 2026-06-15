/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: SseEmitterServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:57:34
 */

package com.nthn.storevision.service.notification.impl;

import com.nthn.storevision.dto.notification.NotificationDTO;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.nthn.storevision.service.notification.SseEmitterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/** Service for managing Server-Sent Events (SSE) emitters and sending real-time notifications. */
@Slf4j
@Service
public class SseEmitterServiceImpl implements SseEmitterService {

  private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

  @Override
  public void addEmitter(final SseEmitter emitter) {
    emitters.add(emitter);
    log.info("New SSE emitter registered. Total emitters: {}", emitters.size());

    emitter.onCompletion(
        () -> {
          emitters.remove(emitter);
          log.info("SSE emitter completed. Total emitters: {}", emitters.size());
        });

    emitter.onTimeout(
        () -> {
          emitter.complete();
          emitters.remove(emitter);
          log.warn("SSE emitter timed out. Total emitters: {}", emitters.size());
        });

    emitter.onError(
        e -> {
          emitters.remove(emitter);
          log.error("SSE emitter error: {}", e.getMessage());
        });
  }

  @Override
  public void sendNotification(final NotificationDTO notification) {
    emitters.forEach(
        emitter -> {
          try {
            emitter.send(SseEmitter.event().name("notification").data(notification));
          } catch (final IOException e) {
            log.error("Error sending notification to SSE emitter: {}", e.getMessage());
            emitter.completeWithError(e);
            emitters.remove(emitter);
          }
        });
  }
}
