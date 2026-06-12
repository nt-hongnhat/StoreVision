/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryValuationDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.dto.analytics;

import java.math.BigDecimal;
import java.util.List;

/**
 * Valuation of the whole current inventory: per-product stock values plus the grand total.
 *
 * @param totalValue sum of all product stock values; never {@code null}
 * @param items per-product valuation rows; never {@code null}
 */
public record InventoryValuationDTO(BigDecimal totalValue, List<InventoryValuationItemDTO> items) {}
