/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductSalesDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.dto.analytics;

/**
 * Internal projection: units of a product sold through COMPLETED orders (BR-A01) within a date
 * range. Used by the slow-moving and profit reports.
 *
 * @param productId the product's identifier
 * @param quantitySold units sold in the range; never {@code null}
 */
public record ProductSalesDTO(Long productId, Long quantitySold) {}
