/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 15:06:01
 */

package com.nthn.storevision.service.notification;

import com.nthn.storevision.dto.InventoryDTO;
import org.jspecify.annotations.NonNull;

/**
 * Service interface for creating internal notifications.
 *
 * <p>Exposes a single entry point used by the low-stock alert scheduler (section 3.2.3).
 */
@FunctionalInterface
public interface NotificationCreationService {

  /**
   * Creates a {@code LOW_STOCK_ALERT} notification for the given product, visible to ADMIN and
   * MANAGER via {@code GET /notifications} (BR-I04).
   *
   * @param product the low-stock product the alert refers to; must not be {@code null}
   */
  void sendLowStockAlert(@NonNull InventoryDTO product);
}
