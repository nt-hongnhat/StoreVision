/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryRetrievalService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:24:56
 */

package com.nthn.storevision.service.inventory;

import com.nthn.storevision.dto.inventory.InventoryDTO;
import com.nthn.storevision.dto.stock_movement.StockMovementDTO;
import com.nthn.storevision.dto.stock_movement.StockReceiptDTO;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;

/** Service interface for reading inventory state and movement history. */
public interface InventoryRetrievalService {

  /**
   * Returns the current stock level for all active products.
   *
   * @return list of stock summaries ordered by product name
   */
  List<InventoryDTO> findAllInventory();

  /**
   * Returns the current stock level for a single product.
   *
   * @param productId the product to query
   * @return stock summary for the product
   * @throws com.nthn.storevision.exception.EntityNotFoundException if the product does not exist
   */
  InventoryDTO findInventoryByProductId(Long productId);

  /**
   * Returns active products whose stock quantity is at or below the configured low-stock threshold
   * (BR-I04, default 10).
   *
   * @return list of low-stock products
   */
  List<InventoryDTO> findLowStockProducts();

  /**
   * Returns all stock movements for a given product, ordered by the most recent first.
   *
   * @param productId the product to query
   * @return movement history for that product
   */
  List<StockMovementDTO> findMovementsByProductId(Long productId);

  /**
   * Returns one page of RECEIPT movements within an optional date range, the most recent first.
   * Null date parameters mean open-ended; out-of-range page parameters are clamped.
   *
   * @param from start of date range (inclusive), or {@code null}
   * @param to end of date range (inclusive), or {@code null}
   * @param page zero-based page index; negative values are treated as 0
   * @param size page size; clamped to a sane maximum
   * @return the requested page of matching receipts; never {@code null}
   */
  Page<StockReceiptDTO> findReceiptHistory(
      LocalDateTime from, LocalDateTime to, int page, int size);
}
