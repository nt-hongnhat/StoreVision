/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderSummaryDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.dto.analytics;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Aggregated order statistics over a date range. Revenue counts only COMPLETED orders (BR-A01).
 *
 * @param from inclusive start of the reporting range
 * @param to inclusive end of the reporting range
 * @param totalOrders all active orders created in the range
 * @param pendingOrders orders still in PENDING status
 * @param completedOrders orders confirmed as paid
 * @param cancelledOrders orders cancelled before payment
 * @param totalRevenue revenue of completed orders; never {@code null}
 */
public record OrderSummaryDTO(
    LocalDate from,
    LocalDate to,
    long totalOrders,
    long pendingOrders,
    long completedOrders,
    long cancelledOrders,
    BigDecimal totalRevenue) {}
