/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: NotificationCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-09 16:21:55
 */

package com.nthn.storevision.service.notification;

import com.nthn.storevision.constant.notification.OrderNotificationType;
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
   * Creates an order-workflow notification for the given order (out-of-stock cancellation, payment
   * success, or payment-failure cancellation).
   *
   * @param order the order the notification refers to; must not be {@code null}
   * @param type the notification type; one of {@link OrderNotificationType#ORDER_OUT_OF_STOCK},
   *     {@link OrderNotificationType#ORDER_COMPLETED}, or {@link
   *     OrderNotificationType#ORDER_PAYMENT_FAILED}; must not be {@code null}
   */
  void sendOrderAlert(@NonNull OrderDTO order, @NonNull OrderNotificationType type);
}
