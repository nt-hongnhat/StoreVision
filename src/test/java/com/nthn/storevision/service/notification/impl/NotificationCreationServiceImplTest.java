/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationCreationServiceImplTest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 15:00:00
 */

package com.nthn.storevision.service.notification.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.nthn.storevision.constant.NotificationType;
import com.nthn.storevision.dto.InventoryDTO;
import com.nthn.storevision.entity.NotificationEntity;
import com.nthn.storevision.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for {@link NotificationCreationServiceImpl}. */
@ExtendWith(MockitoExtension.class)
class NotificationCreationServiceImplTest {

  @Mock private NotificationRepository notificationRepository;

  private NotificationCreationServiceImpl service;

  @Test
  void sendLowStockAlert_shouldPersistLowStockAlertNotification() {
    final InventoryDTO product =
        InventoryDTO.builder()
            .productId(10L)
            .productName("Coca Cola 330ml")
            .sku("SKU-001")
            .stockQuantity(3)
            .build();

    service.sendLowStockAlert(product);

    final ArgumentCaptor<NotificationEntity> captor =
        ArgumentCaptor.forClass(NotificationEntity.class);
    verify(notificationRepository).save(captor.capture());
    final NotificationEntity saved = captor.getValue();

    assertThat(saved.getType()).isEqualTo(NotificationType.LOW_STOCK_ALERT);
    assertThat(saved.getTitle()).isEqualTo("Low stock alert");
    assertThat(saved.getMessage()).contains("Coca Cola 330ml", "SKU-001", "3");
    assertThat(saved.getReferenceId()).isEqualTo(10L);
    assertThat(saved.isRead()).isFalse();
  }

  @BeforeEach
  void setUp() {
    service = new NotificationCreationServiceImpl(notificationRepository);
  }
}
