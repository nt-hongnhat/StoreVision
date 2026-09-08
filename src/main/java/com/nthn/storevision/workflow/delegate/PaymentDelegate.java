/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: PaymentDelegate.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-08 17:19:35
 */

package com.nthn.storevision.workflow.delegate;

import lombok.extern.slf4j.Slf4j;
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
    log.info("Processing payment for order {}", execution.getVariable("orderId"));
    // TODO: Implement payment processing logic
  }
}
