/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: PaymentDelegate.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-09 15:54:44
 */

package com.nthn.storevision.workflow.delegate;

import com.nthn.storevision.exception.InsufficientStockException;
import com.nthn.storevision.exception.order.InvalidOrderStateException;
import com.nthn.storevision.service.order.OrderUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

/**
 * Delegate responsible for confirming payment for an order.
 *
 * <p>This class is part of a workflow and implements the {@link JavaDelegate} interface provided by
 * Flowable. After the payment gateway confirms the charge, it calls {@link
 * OrderUpdateService#complete(Long)}, which re-checks stock (BR-O01), deducts it, awards loyalty
 * points, and increments voucher usage in one transaction. A business failure there (stock
 * vanished, or the order is no longer {@code PENDING}) is raised as the {@code
 * PAYMENT_FAILED_ERROR} BPMN error caught by the {@code errorPaymentFailed} boundary event attached
 * to {@code taskPayment}, instead of crashing the process instance.
 */
@Component
@Slf4j
@RequiredArgsConstructor
class PaymentDelegate implements JavaDelegate {
  private static final String PAYMENT_FAILED_ERROR_CODE = "PAYMENT_FAILED_ERROR";

  private final OrderUpdateService orderUpdateService;

  @Override
  public void execute(final @NonNull DelegateExecution execution) {
    final Long orderId = (Long) execution.getVariable("orderId");
    log.info("Processing payment for order {}", orderId);

    confirmPaymentWithGateway(orderId);

    try {
      orderUpdateService.complete(orderId);
    } catch (final InsufficientStockException | InvalidOrderStateException businessError) {
      throw new BpmnError(PAYMENT_FAILED_ERROR_CODE, businessError.getMessage());
    }
  }

  private static void confirmPaymentWithGateway(final Long orderId) {
    log.debug("Confirming payment for order {}", orderId);
    // TODO: Implement payment processing logic
  }
}
