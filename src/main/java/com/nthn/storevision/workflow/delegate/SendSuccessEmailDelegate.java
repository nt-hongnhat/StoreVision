/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: SendSuccessEmailDelegate.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-09 16:26:56
 */

package com.nthn.storevision.workflow.delegate;

import com.nthn.storevision.constant.notification.OrderNotificationType;
import com.nthn.storevision.dto.order.OrderDTO;
import com.nthn.storevision.service.notification.NotificationCreationService;
import com.nthn.storevision.service.order.OrderRetrievalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

/**
 * Delegate responsible for notifying about a successfully completed order.
 *
 * <p>This class is part of a workflow and implements the {@link JavaDelegate} interface provided by
 * Flowable. It runs right after {@code taskPayment} has confirmed payment (order already moved to
 * {@code COMPLETED} by {@link com.nthn.storevision.service.order.OrderUpdateService#complete}) and
 * raises an {@link
 * com.nthn.storevision.constant.notification.OrderNotificationType#ORDER_COMPLETED} notification
 * for it.
 */
@Component
@Slf4j
@RequiredArgsConstructor
class SendSuccessEmailDelegate implements JavaDelegate {
  private final NotificationCreationService notificationCreationService;
  private final OrderRetrievalService orderRetrievalService;

  @Override
  public void execute(final @NonNull DelegateExecution execution) {
    final Long orderId = (Long) execution.getVariable("orderId");
    log.debug("Sending success notification for order {}", orderId);
    final OrderDTO completedOrder = orderRetrievalService.findOrderDTOById(orderId);
    notificationCreationService.sendOrderAlert(
        completedOrder, OrderNotificationType.ORDER_COMPLETED);
    log.debug("Sent success notification for order {}", orderId);
  }
}
