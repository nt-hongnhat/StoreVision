/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: RevenuePointDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.dto.analytics;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * A single bucket of the revenue report — only COMPLETED orders are counted (BR-A01).
 *
 * @param period start of the time bucket (day, week or month)
 * @param revenue total revenue of the bucket; never {@code null}
 * @param orderCount number of completed orders in the bucket; never {@code null}
 */
@Schema(description = "A single bucket of the revenue report.")
public record RevenuePointDTO(
    @Schema(description = "Start of the time bucket (day, week or month).", example = "2026-06-01T00:00:00")
        LocalDateTime period,
    @Schema(description = "Total revenue of the bucket (BR-A01).", example = "5000000")
        BigDecimal revenue,
    @Schema(description = "Number of completed orders in the bucket.", example = "10")
        Long orderCount) {}
