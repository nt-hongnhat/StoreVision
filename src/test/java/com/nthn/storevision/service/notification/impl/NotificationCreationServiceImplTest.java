/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.test
 * File: NotificationCreationServiceImplTest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-09 16:41:06
 */

package com.nthn.storevision.service.notification.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.nthn.storevision.constant.notification.CommonNotificationType;
import com.nthn.storevision.dto.inventory.InventoryDTO;
import com.nthn.storevision.dto.notification.NotificationDTO;
import com.nthn.storevision.entity.NotificationEntity;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.mapper.NotificationMapper;
import com.nthn.storevision.repository.NotificationRepository;
import com.nthn.storevision.service.notification.SseEmitterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for {@link NotificationCreationServiceImpl}. */
@ExtendWith(MockitoExtension.class)
class NotificationCreationServiceImplTest {

  @Mock private EntityMapperConfig<NotificationDTO, Long, NotificationEntity> notificationMapper;
  @Mock private NotificationRepository notificationRepository;
  @InjectMocks private NotificationCreationServiceImpl service;
  @Mock private SseEmitterService sseEmitterService;

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

    assertThat(saved.getType()).isEqualTo(CommonNotificationType.LOW_STOCK_ALERT);
    assertThat(saved.getTitle()).isEqualTo(CommonNotificationType.Title.LOW_STOCK_TITLE);
    assertThat(saved.getMessage()).contains("Coca Cola 330ml", "SKU-001", "3");
    assertThat(saved.getReferenceId()).isEqualTo(10L);
    assertThat(saved.isRead()).isFalse();
  }
}
