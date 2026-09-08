/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: CheckStockDelegate.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-08 17:18:13
 */

package com.nthn.storevision.workflow.delegate;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

/**
 * Delegate responsible for performing a stock check for a product.
 *
 * <p>This class is part of a workflow and implements the {@link JavaDelegate} interface provided by
 * Flowable. It retrieves the product ID from the execution context and determines the availability
 * of the product in stock.
 *
 * <p>Behavior: - Logs the product ID for which the stock check is being performed. - Sets workflow
 * variables: - "stockStatus": A textual description of the stock status (e.g., "in stock"). -
 * "inStock": A boolean indicating whether the product is available or not.
 *
 * <p>Note: The actual logic for checking the stock is not currently implemented. This functionality
 * is represented by a placeholder and should be implemented in the future.
 */
@Component
@Slf4j
class CheckStockDelegate implements JavaDelegate {
  @Override
  public void execute(final @NonNull DelegateExecution execution) {
    final String productId = execution.getVariable("productId").toString();
    log.info("Checking stock for product {}", productId);

    final boolean inStock = true;
    execution.setVariable("stockStatus", "in stock");
    execution.setVariable("inStock", inStock);

    // TODO: Implement stock checking logic
  }
}
