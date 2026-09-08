/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: PaymentDelegate.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-08 17:51:43
 */

package com.nthn.storevision.workflow.delegate;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.BpmnError;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

/**
 * Delegate responsible for processing payment for an order.
 *
 * <p>This class is part of a workflow and implements the {@link JavaDelegate} interface provided by
 * Flowable. It retrieves the order ID from the execution context and performs the necessary logic
 * for processing payments.
 *
 * <p>Behavior: - Logs the order ID for which the payment is being processed. - Represents a
 * placeholder for implementing the actual payment processing logic.
 *
 * <p>Note: The functionality to process payments is not currently implemented.
 */
@Component
@Slf4j
class PaymentDelegate implements JavaDelegate {
  @Override
  public void execute(final @NonNull DelegateExecution execution) {
    final Object orderId = execution.getVariable("orderId");
    log.info("Processing payment for order {}", orderId);
    // TODO: Implement payment processing logic

    final boolean isPaymentSuccessful = isPaymentProcessed();

    if (isPaymentSuccessful) {
      execution.setVariable("isPaymentSuccessful", true);
      log.info("Payment successful for order {}", orderId);
    } else {
      log.error("Payment failed for order {}", orderId);
      throw new BpmnError("PAYMENT_FAILED_ERROR", "Payment failed for order");
    }
  }

  private static boolean isPaymentProcessed() {
    // TODO: Implement payment processing logic
    return false;
  }
}
