/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryController.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 17:15:29
 */

package com.nthn.storevision.controller;

import com.nthn.storevision.dto.InventoryDTO;
import com.nthn.storevision.dto.StockMovementDTO;
import com.nthn.storevision.dto.StockReceiptDTO;
import com.nthn.storevision.dto.request.StockReceiptRequest;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Version-agnostic contract for inventory management endpoints.
 *
 * @see com.nthn.storevision.controller.v1.InventoryControllerV1
 */
public interface InventoryController {

  /**
   * Records a stock receipt and increments the product's stock quantity (BR-I01, BR-I02).
   *
   * @param request the receipt details; must not be {@code null}
   * @return the persisted receipt as a {@link StockReceiptDTO}
   * @see com.nthn.storevision.controller.v1.InventoryControllerV1#createReceipt
   */
  StockReceiptDTO createReceipt(@Valid StockReceiptRequest request);

  /**
   * Returns the current stock level for all active products.
   *
   * @return list of stock summaries; never {@code null}
   * @see com.nthn.storevision.controller.v1.InventoryControllerV1#getAllInventory
   */
  List<InventoryDTO> getAllInventory();

  /**
   * Returns the current stock level for a single product.
   *
   * @param productId the product id to query; must not be {@code null}
   * @return stock summary for that product
   * @see com.nthn.storevision.controller.v1.InventoryControllerV1#getInventoryByProductId
   */
  InventoryDTO getInventoryByProductId(Long productId);

  /**
   * Returns products whose stock is at or below the configured low-stock threshold (BR-I04).
   *
   * @return list of low-stock products; never {@code null}
   * @see com.nthn.storevision.controller.v1.InventoryControllerV1#getLowStockProducts
   */
  List<InventoryDTO> getLowStockProducts();

  /**
   * Returns all stock movements for a product, ordered by most recent first.
   *
   * @param productId the product id to query; must not be {@code null}
   * @return full movement history for that product
   * @see com.nthn.storevision.controller.v1.InventoryControllerV1#getMovementsByProductId
   */
  List<StockMovementDTO> getMovementsByProductId(Long productId);

  /**
   * Returns RECEIPT movements within an optional date range.
   *
   * @param from start of date range (inclusive), or {@code null} for no lower bound
   * @param to end of date range (inclusive), or {@code null} for no upper bound
   * @return matching receipts ordered by most recent first
   * @see com.nthn.storevision.controller.v1.InventoryControllerV1#getReceiptHistory
   */
  List<StockReceiptDTO> getReceiptHistory(LocalDateTime from, LocalDateTime to);
}
