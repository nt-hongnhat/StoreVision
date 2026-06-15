/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: LowStockAlertSchedulerTest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 15:16:20
 */

package com.nthn.storevision.scheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nthn.storevision.dto.inventory.InventoryDTO;
import com.nthn.storevision.service.inventory.InventoryRetrievalService;
import com.nthn.storevision.service.notification.NotificationCreationService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for {@link LowStockAlertScheduler}. */
@ExtendWith(MockitoExtension.class)
class LowStockAlertSchedulerTest {

  @Mock private InventoryRetrievalService inventoryRetrievalService;
  @Mock private NotificationCreationService notificationCreationService;

  private LowStockAlertScheduler scheduler;

  @Test
  void alertLowStockProducts_shouldDoNothingWhenNoLowStockProducts() {
    when(inventoryRetrievalService.findLowStockProducts()).thenReturn(List.of());

    scheduler.alertLowStockProducts();

    verify(notificationCreationService, never()).sendLowStockAlert(any());
  }

  @Test
  void alertLowStockProducts_shouldSendAlertForEachLowStockProduct() {
    final InventoryDTO productA =
        InventoryDTO.builder().productId(1L).productName("Product A").stockQuantity(2).build();
    final InventoryDTO productB =
        InventoryDTO.builder().productId(2L).productName("Product B").stockQuantity(0).build();
    when(inventoryRetrievalService.findLowStockProducts()).thenReturn(List.of(productA, productB));

    scheduler.alertLowStockProducts();

    verify(notificationCreationService).sendLowStockAlert(productA);
    verify(notificationCreationService).sendLowStockAlert(productB);
  }

  @BeforeEach
  void setUp() {
    scheduler = new LowStockAlertScheduler(inventoryRetrievalService, notificationCreationService);
  }
}
