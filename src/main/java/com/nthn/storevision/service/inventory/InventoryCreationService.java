/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 17:22:56
 */

package com.nthn.storevision.service.inventory;

import com.nthn.storevision.dto.request.StockReceiptRequest;
import com.nthn.storevision.dto.stock_movement.StockReceiptDTO;
import org.jspecify.annotations.NonNull;

/**
 * Service interface for creating stock movement records.
 *
 * <p>Exposes two entry points: {@link #receiveStock} for manual RECEIPT movements (called directly
 * from the controller), and {@link #recordIssue} for automated ISSUE movements triggered by order
 * completion (BR-I05).
 */
public interface InventoryCreationService {

  /**
   * Records a RECEIPT stock movement and increments the product's stock quantity. Validates that
   * the product exists and is active, enforces BR-I01 (performed_by) and BR-I02 (quantity &gt;= 1).
   *
   * @param request the receipt details
   * @return the persisted movement as a {@link StockReceiptDTO}
   */
  StockReceiptDTO receiveStock(@NonNull StockReceiptRequest request);

  /**
   * Records an ISSUE stock movement when an order is completed (BR-I05). Must be called within the
   * same transaction as the order completion to ensure consistency.
   *
   * @param productId the product being issued
   * @param quantity units deducted from stock
   * @param referenceOrderId the order that triggered this issue
   */
  void recordIssue(Long productId, Integer quantity, Long referenceOrderId);
}
