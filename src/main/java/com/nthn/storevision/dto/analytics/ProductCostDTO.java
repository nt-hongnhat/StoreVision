/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductCostDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.dto.analytics;

/**
 * Internal projection: the average RECEIPT unit cost of a product, used as the cost basis for the
 * profit and inventory valuation reports. JPQL {@code AVG} yields a {@code Double}; the service
 * layer converts it to {@code BigDecimal}.
 *
 * @param productId the product's identifier
 * @param averageUnitCost average RECEIPT cost per unit; never {@code null} (rows without receipts
 *     are absent entirely)
 */
public record ProductCostDTO(Long productId, Double averageUnitCost) {}
