/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: LowStockAlertScheduler.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 15:27:49
 */

package com.nthn.storevision.scheduler;

import com.nthn.storevision.service.inventory.InventoryRetrievalService;
import com.nthn.storevision.service.notification.NotificationCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Periodically scans for low-stock products and raises {@code LOW_STOCK_ALERT} notifications
 * (BR-I04, section 3.2.3).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LowStockAlertScheduler {

  private final InventoryRetrievalService inventoryRetrievalService;
  private final NotificationCreationService notificationCreationService;

  /**
   * Runs daily at 08:00 (server time) by default; the cron expression can be overridden via the
   * {@code inventory.low-stock-alert-cron} property. For every active product whose stock quantity
   * is at or below the configured low-stock threshold, raises a {@code LOW_STOCK_ALERT}
   * notification for ADMIN/MANAGER and logs a warning.
   */
  @Scheduled(cron = "${inventory.low-stock-alert-cron:0 0 8 * * *}")
  public void alertLowStockProducts() {
    final var lowStockProducts = inventoryRetrievalService.findLowStockProducts();
    log.debug("Low stock alert scan found {} product(s)", lowStockProducts.size());

    lowStockProducts.forEach(
        product -> {
          notificationCreationService.sendLowStockAlert(product);
          log.warn(
              "Low stock alert: product={}, remaining={}",
              product.getProductName(),
              product.getStockQuantity());
        });
  }
}
