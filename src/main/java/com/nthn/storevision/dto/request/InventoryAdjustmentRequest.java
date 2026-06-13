/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryAdjustmentRequest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 17:09:34
 */

package com.nthn.storevision.dto.request;

import jakarta.validation.constraints.Min; 
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NonNull;

/**
 * Request DTO for adjusting product stock quantity.
 */
@Builder
public record InventoryAdjustmentRequest(
    @NotNull(message = "{INM001}") // Product ID required
    Long productId,

    @NotNull(message = "{INM002}") // Quantity required
    Integer quantity, // Can be positive (increase) or negative (decrease)

    String note // Optional note for the adjustment
) {
}