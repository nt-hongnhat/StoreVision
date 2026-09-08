/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: SendSuccessEmailDelegate.java
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
 * Delegate responsible for sending a success email notification when an order is successfully
 * processed.
 *
 * <p>This class is part of a workflow and implements the {@link JavaDelegate} interface provided by
 * Flowable. It is invoked after an order has been successfully handled and is responsible for
 * notifying the appropriate parties about the successful completion of the order.
 *
 * <p>Behavior: - Logs the action of sending a success email notification, including the associated
 * order ID.
 *
 * <p>Note: The actual logic for sending the email is not implemented and should be added in the
 * future as part of the delegate's complete functionality.
 */
@Component
@Slf4j
class SendSuccessEmailDelegate implements JavaDelegate {

  @Override
  public void execute(final @NonNull DelegateExecution execution) {
    log.info("Sending success email for order {}", execution.getVariable("orderId"));
    // TODO: Implement sending success email logic
  }
}
