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

import com.nthn.storevision.dto.inventory.InventoryDTO;
import com.nthn.storevision.service.inventory.InventoryRetrievalService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
class CheckStockDelegate implements JavaDelegate {
  private static final String AVAILABLE = "available";
  private static final String INSUFFICIENT = "insufficient";
  private final InventoryRetrievalService inventoryRetrievalService;

  @Override
  public void execute(final @NonNull DelegateExecution execution) {
    final Long productId = Long.valueOf(execution.getVariable("productId").toString());
    final Long requestedQuantity =
        Long.valueOf(execution.getVariable("requestedQuantity").toString());
    log.debug(
        "Checking stock for product {} with requested quantity {}", productId, requestedQuantity);
    final InventoryDTO foundInventory =
        inventoryRetrievalService.findInventoryByProductId(productId);
    final boolean inStock = foundInventory.getStockQuantity() >= requestedQuantity;

    final String stockStatus = inStock ? AVAILABLE : INSUFFICIENT;
    execution.setVariable("stockStatus", stockStatus);
    execution.setVariable("inStock", inStock);

    log.debug(
        "Checked stock for product {} with requested quantity {} is {}",
        productId,
        requestedQuantity,
        stockStatus);
  }
}
