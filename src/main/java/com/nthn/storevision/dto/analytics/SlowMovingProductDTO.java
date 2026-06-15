/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: SlowMovingProductDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.dto.analytics;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * One row of the slow-moving products report: active products with the fewest completed sales in
 * the look-back window, ties broken by the highest remaining stock.
 *
 * @param productId the product's identifier
 * @param productName the product's display name
 * @param stockQuantity current units in stock; never {@code null}
 * @param quantitySold units sold within the look-back window; zero when never sold
 */
@Schema(description = "One row of the slow-moving products report.")
public record SlowMovingProductDTO(
    @Schema(description = "The product's identifier.", example = "1") Long productId,
    @Schema(description = "The product's display name.", example = "Wireless Mouse")
        String productName,
    @Schema(description = "Current units in stock.", example = "50") Integer stockQuantity,
    @Schema(description = "Units sold within the look-back window; zero when never sold.", example = "0")
        Long quantitySold) {}
