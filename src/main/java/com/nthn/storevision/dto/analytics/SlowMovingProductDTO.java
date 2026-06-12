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

/**
 * One row of the slow-moving products report: active products with the fewest completed sales in
 * the look-back window, ties broken by the highest remaining stock.
 *
 * @param productId the product's identifier
 * @param productName the product's display name
 * @param stockQuantity current units in stock; never {@code null}
 * @param quantitySold units sold within the look-back window; zero when never sold
 */
public record SlowMovingProductDTO(
    Long productId, String productName, Integer stockQuantity, Long quantitySold) {}
