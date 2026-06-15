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

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Aggregated order statistics over a date range.")
public record OrderSummaryDTO(
    @Schema(description = "Inclusive start of the reporting range.", example = "2026-06-01")
        LocalDate from,
    @Schema(description = "Inclusive end of the reporting range.", example = "2026-06-30")
        LocalDate to,
    @Schema(description = "All active orders created in the range.", example = "120")
        long totalOrders,
    @Schema(description = "Orders still in PENDING status.", example = "5") long pendingOrders,
    @Schema(description = "Orders confirmed as paid.", example = "100") long completedOrders,
    @Schema(description = "Orders cancelled before payment.", example = "15")
        long cancelledOrders,
    @Schema(description = "Revenue of completed orders (BR-A01).", example = "55000000")
        BigDecimal totalRevenue) {}
