/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: NotificationCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-09 13:52:18
 */

package com.nthn.storevision.service.notification;

import com.nthn.storevision.constant.notification.NotificationType;
import com.nthn.storevision.dto.inventory.InventoryDTO;
import com.nthn.storevision.dto.order.OrderDTO;
import org.jspecify.annotations.NonNull;

/**
 * Service interface for creating internal notifications.
 *
 * <p>Exposes a single entry point used by the low-stock alert scheduler (section 3.2.3).
 */
public interface NotificationCreationService {
  /**
   * Creates a {@code LOW_STOCK_ALERT} notification for the given product, visible to ADMIN and
   * MANAGER via {@code GET /notifications} (BR-I04).
   *
   * @param product the low-stock product the alert refers to; must not be {@code null}
   */
  void sendLowStockAlert(@NonNull InventoryDTO product);

    /**
     * Sends an email to notify that the specified order is out of stock.
     *
     * @param order the order that cannot be fulfilled due to insufficient stock; must not be {@code null}
     * @param type the type of notification to be sent, typically {@link NotificationType#LOW_STOCK_ALERT}; must not be {@code null}
     */
  void sendOrderOutOfStockEmail(@NonNull OrderDTO order, NotificationType type);
}
