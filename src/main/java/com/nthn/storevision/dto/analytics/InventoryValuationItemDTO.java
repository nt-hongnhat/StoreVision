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
public record InventoryValuationItemDTO(
    Long productId,
    String productName,
    Integer stockQuantity,
    BigDecimal averageUnitCost,
    BigDecimal stockValue) {}
