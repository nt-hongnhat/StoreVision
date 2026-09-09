/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: NotificationCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-09 13:49:30
 */

package com.nthn.storevision.service.notification.impl;

import com.nthn.storevision.constant.notification.NotificationType;
import com.nthn.storevision.dto.inventory.InventoryDTO;
import com.nthn.storevision.dto.notification.NotificationDTO;
import com.nthn.storevision.dto.order.OrderDTO;
import com.nthn.storevision.entity.NotificationEntity;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.model.Notification;
import com.nthn.storevision.repository.NotificationRepository;
import com.nthn.storevision.service.notification.NotificationCreationService;
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

  private static final String LOW_STOCK_MESSAGE_FORMAT =
      "Product '%s' (SKU %s) has %d unit(s) remaining, at or below the configured low-stock threshold (BR-I04).";
  private static final String LOW_STOCK_TITLE = "Low stock alert";
  private final EntityMapperConfig<NotificationDTO, Long, NotificationEntity>
      notificationMapper; // Injected mapper
  private final NotificationRepository notificationRepository;
  private final SseEmitterServiceImpl sseEmitterService;

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void sendLowStockAlert(final @NonNull InventoryDTO product) {
    log.debug(
        "Creating LOW_STOCK_ALERT notification: productId={}, remaining={}",
        product.getProductId(),
        product.getStockQuantity());

    final NotificationEntity notification =
        NotificationEntity.builder()
            .type(NotificationType.LOW_STOCK_ALERT)
            .title(LOW_STOCK_TITLE)
            .message(
                String.format(
                    LOW_STOCK_MESSAGE_FORMAT,
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

  @Override
  public void sendOrderOutOfStockEmail(@NonNull final OrderDTO order, final NotificationType type) {
    log.debug("Sending order out of stock email for order {}", order.getOrderId());
    final Notification notification =
        NotificationEntity.builder()
            .type(type)
            .title("Order out of stock")
            .message("The order %s is out of stock.".formatted(order.getOrderId()))
            .referenceId(order.getOrderId())
            .read(false)
            .build();
    final NotificationEntity savedNotification = notificationRepository.save(notification);
    // Send the new notification via SSE
    sseEmitterService.sendNotification(notificationMapper.toDTO(savedNotification));
  }
}
