/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 14:52:22
 */

package com.nthn.storevision.dto;

import com.nthn.storevision.constant.NotificationType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for an internal {@link com.nthn.storevision.entity.NotificationEntity}.
 * Returned by {@code GET /notifications} and {@code PATCH /notifications/{id}}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

  /** Response-only: timestamp when this notification was created. */
  private LocalDateTime createdDate;

  /** Response-only: auto-assigned primary key. */
  private Long id;

  /** Human-readable message body. */
  private String message;

  /** Whether this notification has been read by its recipient. */
  private boolean read;

  /** Identifier of the related entity (e.g. a product id), or {@code null} when none applies. */
  private Long referenceId;

  /** Short title of the notification. */
  private String title;

  /** Category of the notification. */
  private NotificationType type;
}
