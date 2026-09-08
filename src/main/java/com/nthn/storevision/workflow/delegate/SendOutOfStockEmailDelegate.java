/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: SendOutOfStockEmailDelegate.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-08 17:19:35
 */

package com.nthn.storevision.workflow.delegate;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * Delegate responsible for sending an "Out of Stock" email notification.
 *
 * <p>This class is part of the workflow and implements the {@link JavaDelegate} interface provided
 * by Flowable. It is triggered when the system determines that a product is out of stock and
 * requires notifying the appropriate parties.
 *
 * <p>Behavior: - Logs the action of sending an "Out of Stock" email notification.
 *
 * <p>Note: The logic for sending the actual email is not implemented and should be added in the
 * future as part of the full functionality of this delegate.
 */
@Component
@Slf4j
class SendOutOfStockEmailDelegate implements JavaDelegate {
  @Override
  public void execute(final DelegateExecution execution) {
    log.info("Sending out of stock email");
    // TODO: Implement sending out of stock email
  }
}
