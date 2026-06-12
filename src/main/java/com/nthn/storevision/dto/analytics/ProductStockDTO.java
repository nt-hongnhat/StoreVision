/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductStockDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.dto.analytics;

/**
 * Internal projection: identity and current stock of an active product. Used by the slow-moving
 * and inventory valuation reports.
 *
 * @param productId the product's identifier
 * @param productName the product's display name
 * @param stockQuantity current units in stock; never {@code null}
 */
public record ProductStockDTO(Long productId, String productName, Integer stockQuantity) {}
