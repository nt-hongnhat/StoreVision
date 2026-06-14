/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationReadRequest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14
 */

package com.nthn.storevision.dto.request;

import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Request body for PATCH /notifications/'{'id'}'. Marks a notification as read. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationReadRequest {

  /**
   * Must be {@code true}; marks the notification as read. {@code false} is rejected — this
   * endpoint does not support marking a notification as unread.
   */
  @AssertTrue(message = "{NOM002}")
  private boolean read;
}
