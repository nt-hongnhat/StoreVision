/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: NotificationDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 22:44:25
 */

package com.nthn.storevision.dto.notification;

import com.nthn.storevision.constant.notification.NotificationType;
import com.nthn.storevision.dto.response.HasIdentifier;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

import lombok.*;

/**
 * Data Transfer Object for an internal {@link com.nthn.storevision.entity.NotificationEntity}.
 * Returned by {@code GET /notifications} and {@code PATCH /notifications/{id}}.
 */
@Schema(description = "An internal system notification, such as a low-stock alert.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NotificationDTO implements HasIdentifier<Long> {

  /** Response-only: timestamp when this notification was created. */
  @Schema(
      description = "Timestamp when this notification was created.",
      example = "2026-06-15T08:00:00",
      accessMode = Schema.AccessMode.READ_ONLY)
  private LocalDateTime createdDate;

  /** Response-only: auto-assigned primary key. */
  @Schema(
      description = "Auto-assigned primary key.",
      example = "1",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  /** Human-readable message body. */
  @Schema(
      description = "Human-readable message body.",
      example = "Product 'Wireless Mouse' is low on stock (5 units remaining).")
  private String message;

  /** Whether this notification has been read by its recipient. */
  @Schema(
      description = "Whether this notification has been read by its recipient.",
      example = "false")
  private boolean read;

  /** Identifier of the related entity (e.g. a product id), or {@code null} when none applies. */
  @Schema(
      description =
          "Identifier of the related entity (e.g. a product id), or null when none applies.",
      example = "1")
  private Long referenceId;

  /** Short title of the notification. */
  @Schema(description = "Short title of the notification.", example = "Low stock alert")
  private String title;

  /** Category of the notification. */
  @Schema(description = "Category of the notification.", example = "LOW_STOCK_ALERT")
  private NotificationType type;
}
