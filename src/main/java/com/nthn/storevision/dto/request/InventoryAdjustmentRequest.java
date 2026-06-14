/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryAdjustmentRequest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 20:02:08
 */

package com.nthn.storevision.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * Request DTO for adjusting product stock quantity.
 *
 * @param note Optional note for the adjustment
 * @param productId Product ID to adjust stock quantity
 * @param quantity Quantity to adjust (can be positive for increase or negative for decrease)
 */
@Builder
public record InventoryAdjustmentRequest(
    @NotNull(message = "{INM001}") Long productId,
    @NotNull(message = "{INM002}") Integer quantity,
    String note) {}
