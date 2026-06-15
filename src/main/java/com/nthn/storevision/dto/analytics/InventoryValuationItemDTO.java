/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryValuationItemDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.dto.analytics;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/**
 * Valuation of a single product's current stock: average RECEIPT unit cost times units in stock.
 *
 * @param productId the product's identifier
 * @param productName the product's display name
 * @param stockQuantity current units in stock; never {@code null}
 * @param averageUnitCost average RECEIPT cost per unit; zero when the product was never received
 * @param stockValue {@code averageUnitCost × stockQuantity}; never {@code null}
 */
@Schema(description = "Valuation of a single product's current stock.")
public record InventoryValuationItemDTO(
    @Schema(description = "The product's identifier.", example = "1") Long productId,
    @Schema(description = "The product's display name.", example = "Wireless Mouse")
        String productName,
    @Schema(description = "Current units in stock.", example = "50") Integer stockQuantity,
    @Schema(
            description =
                "Average RECEIPT cost per unit; zero when the product was never received.",
            example = "150000")
        BigDecimal averageUnitCost,
    @Schema(description = "averageUnitCost x stockQuantity.", example = "7500000")
        BigDecimal stockValue) {}
