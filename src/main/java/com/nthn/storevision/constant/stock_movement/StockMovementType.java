/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: StockMovementType.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:52:00
 */

package com.nthn.storevision.constant.stock_movement;

/**
 * Represents the type of stock movement in inventory operations.
 *
 * <p>This enum defines the valid types of stock movements that can be performed in the system,
 * typically used in inventory management processes to track changes in stock levels.
 *
 * <ul>
 *   <li>{@code RECEIPT}: Indicates that stock was added to the inventory, such as through receiving
 *       goods from a supplier.
 *   <li>{@code ISSUE}: Indicates that stock was removed from the inventory, such as for sales or
 *       other outgoing purposes.
 *   <li>{@code ADJUSTMENT}: Represents stock-level changes made for corrections or reconciliation,
 *       such as adjusting for errors or discrepancies.
 * </ul>
 */
public enum StockMovementType {
  /**
   * Represents stock-level changes made for corrections or reconciliation in inventory operations.
   *
   * <p>This type of stock movement is typically used to account for errors, discrepancies, or
   * adjustments in stock quantity, such as during physical inventory counting or correcting
   * transactional mistakes.
   */
  ADJUSTMENT,
  /**
   * Indicates that stock was removed from the inventory.
   *
   * <p>Represents a stock movement type where inventory levels decrease, typically due to sales,
   * transfers, or other outgoing purposes.
   */
  ISSUE,
  /**
   * Indicates that stock was added to the inventory.
   *
   * <p>Represents a stock movement type where inventory levels increase, typically as a result of
   * receiving goods from a supplier or manufacturer.
   */
  RECEIPT
}
