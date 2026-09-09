/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: NotificationCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-09 16:41:05
 */

package com.nthn.storevision.service.notification.impl;

import com.nthn.storevision.constant.notification.CommonNotificationType;
import com.nthn.storevision.constant.notification.OrderNotificationType;
import com.nthn.storevision.dto.inventory.InventoryDTO;
import com.nthn.storevision.dto.notification.NotificationDTO;
import com.nthn.storevision.dto.order.OrderDTO;
import com.nthn.storevision.entity.NotificationEntity;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.repository.NotificationRepository;
import com.nthn.storevision.service.notification.NotificationCreationService;
import com.nthn.storevision.service.notification.SseEmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link NotificationCreationService}. Persists a {@link
 * NotificationEntity} for every low-stock alert raised by the scheduled job (section 3.2.3).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationCreationServiceImpl implements NotificationCreationService {

  private final EntityMapperConfig<NotificationDTO, Long, NotificationEntity>
      notificationMapper; // Injected mapper
  private final NotificationRepository notificationRepository;
  private final SseEmitterService sseEmitterService;

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void sendLowStockAlert(final @NonNull InventoryDTO product) {
    log.debug(
        "Creating LOW_STOCK_ALERT notification: productId={}, remaining={}",
        product.getProductId(),
        product.getStockQuantity());

    final CommonNotificationType lowStockAlert = CommonNotificationType.LOW_STOCK_ALERT;
    final NotificationEntity notification =
        NotificationEntity.builder()
            .type(lowStockAlert)
            .title(lowStockAlert.getTitle())
            .message(
                String.format(
                    lowStockAlert.getMessageFormat(),
                    product.getProductName(),
                    product.getSku(),
                    product.getStockQuantity()))
            .referenceId(product.getProductId())
            .read(false)
            .build();

    final NotificationEntity savedNotification = notificationRepository.save(notification);

    // Send the new notification via SSE
    sseEmitterService.sendNotification(notificationMapper.toDTO(savedNotification));
  }

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void sendOrderAlert(
      final @NonNull OrderDTO order, final @NonNull OrderNotificationType type) {
    log.debug("Creating {} notification for order id={}", type, order.getId());

    final NotificationEntity notification =
        NotificationEntity.builder()
            .type(type)
            .title(type.getTitle())
            .message(String.format(type.getMessageFormat(), order.getId()))
            .referenceId(order.getId())
            .read(false)
            .build();

    final NotificationEntity savedNotification = notificationRepository.save(notification);

    // Send the new notification via SSE
    sseEmitterService.sendNotification(notificationMapper.toDTO(savedNotification));
  }
}
