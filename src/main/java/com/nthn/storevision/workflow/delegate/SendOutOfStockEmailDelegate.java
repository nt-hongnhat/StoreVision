/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: SendOutOfStockEmailDelegate.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-09 16:21:55
 */

package com.nthn.storevision.workflow.delegate;

import com.nthn.storevision.constant.notification.OrderNotificationType;
import com.nthn.storevision.dto.order.OrderDTO;
import com.nthn.storevision.service.notification.NotificationCreationService;
import com.nthn.storevision.service.order.OrderUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

/**
 * Delegate responsible for cancelling an order for insufficient stock and notifying about it.
 *
 * <p>This class is part of the workflow and implements the {@link JavaDelegate} interface provided
 * by Flowable. It is triggered when the system determines that an order cannot be fulfilled due to
 * insufficient stock: it cancels the order ({@code PENDING -> CANCELLED}, BR-O02) and raises an
 * {@link OrderNotificationType#ORDER_OUT_OF_STOCK} notification for it.
 */
@Component
@Slf4j
@RequiredArgsConstructor
class SendOutOfStockEmailDelegate implements JavaDelegate {
  private final NotificationCreationService notificationCreationService;
  private final OrderUpdateService orderUpdateService;

  @Override
  public void execute(final @NonNull DelegateExecution execution) {
    final Long orderId = (Long) execution.getVariable("orderId");
    log.debug("Cancelling order {} for insufficient stock", orderId);
    final OrderDTO cancelledOrder = orderUpdateService.cancel(orderId);
    notificationCreationService.sendOrderAlert(
        cancelledOrder, OrderNotificationType.ORDER_OUT_OF_STOCK);
    log.debug("Sent out of stock notification for order {}", orderId);
  }
}
