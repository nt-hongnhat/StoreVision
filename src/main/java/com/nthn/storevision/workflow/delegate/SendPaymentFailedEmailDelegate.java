/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: SendPaymentFailedEmailDelegate.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-09 16:21:55
 */

package com.nthn.storevision.workflow.delegate;

import com.nthn.storevision.constant.notification.OrderNotificationType;
import com.nthn.storevision.dto.order.OrderDTO;
import com.nthn.storevision.exception.order.InvalidOrderStateException;
import com.nthn.storevision.service.notification.NotificationCreationService;
import com.nthn.storevision.service.order.OrderRetrievalService;
import com.nthn.storevision.service.order.OrderUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

/**
 * Delegate responsible for cancelling an order whose payment confirmation failed and notifying
 * about it.
 *
 * <p>This class is part of the workflow and implements the {@link JavaDelegate} interface provided
 * by Flowable. It runs off the {@code errorPaymentFailed} boundary event attached to {@code
 * taskPayment}: it cancels the order ({@code PENDING -> CANCELLED}, BR-O02) and raises an {@link
 * OrderNotificationType#ORDER_PAYMENT_FAILED} notification for it. Cancellation is idempotent: if
 * the order was already canceled by a previous attempt, the existing state is reused instead of
 * failing the process.
 */
@Component
@Slf4j
@RequiredArgsConstructor
class SendPaymentFailedEmailDelegate implements JavaDelegate {
  private final NotificationCreationService notificationCreationService;
  private final OrderRetrievalService orderRetrievalService;
  private final OrderUpdateService orderUpdateService;

  @Override
  public void execute(final @NonNull DelegateExecution execution) {
    final Long orderId = (Long) execution.getVariable("orderId");
    log.debug("Cancelling order {} for payment failure", orderId);
    final OrderDTO order = cancelOrReuseExisting(orderId);
    notificationCreationService.sendOrderAlert(order, OrderNotificationType.ORDER_PAYMENT_FAILED);
    log.debug("Sent payment failed notification for order {}", orderId);
  }

  private OrderDTO cancelOrReuseExisting(final Long orderId) {
    try {
      return orderUpdateService.cancel(orderId);
    } catch (final InvalidOrderStateException alreadyTransitioned) {
      log.warn(
          "Order {} was no longer PENDING when cancelling for payment failure; reusing its current state",
          orderId,
          alreadyTransitioned);
      return orderRetrievalService.findOrderDTOById(orderId);
    }
  }
}
