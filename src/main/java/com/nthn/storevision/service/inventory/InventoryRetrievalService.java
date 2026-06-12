/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryRetrievalService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 00:00:00
 */

package com.nthn.storevision.service.inventory;

import com.nthn.storevision.dto.InventoryDTO;
import com.nthn.storevision.dto.StockMovementDTO;
import com.nthn.storevision.dto.StockReceiptDTO;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.modulith.NamedInterface;

/**
 * Service interface for reading inventory state and movement history.
 *
 * <p>Annotated with {@link NamedInterface} so other modules (e.g., {@code order}) can depend on
 * this interface without coupling to the implementation.
 */
@NamedInterface
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
   * Returns RECEIPT movements within an optional date range. Null parameters mean open-ended.
   *
   * @param from start of date range (inclusive), or {@code null}
   * @param to   end of date range (inclusive), or {@code null}
   * @return matching receipts ordered by most recent first
   */
  List<StockReceiptDTO> findReceiptHistory(LocalDateTime from, LocalDateTime to);

  /**
   * Returns active products whose stock quantity is at or below the configured low-stock threshold
   * (BR-I04, default 10).
   *
   * @return list of low-stock products
   */
  List<InventoryDTO> findLowStockProducts();

  /**
   * Returns all stock movements for a given product, ordered by most recent first.
   *
   * @param productId the product to query
   * @return movement history for that product
   */
  List<StockMovementDTO> findMovementsByProductId(Long productId);
}
