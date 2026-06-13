/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryAdjustmentService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 17:11:37
 */

package com.nthn.storevision.service.inventory;

import com.nthn.storevision.dto.InventoryDTO;
import com.nthn.storevision.dto.request.InventoryAdjustmentRequest;
import org.jspecify.annotations.NonNull;

/** Service interface for adjusting product inventory. */
@FunctionalInterface
public interface InventoryAdjustmentService {

  /**
   * Adjusts the stock quantity for a product based on the provided request. This method handles
   * both increasing and decreasing stock.
   *
   * @param request the adjustment details; must not be {@code null}
   * @return the updated inventory details for the product
   */
  InventoryDTO adjustStock(@NonNull InventoryAdjustmentRequest request);
}
