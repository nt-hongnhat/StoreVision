/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProfitDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.dto.analytics;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Gross profit report over a date range: revenue from COMPLETED orders (BR-A01) minus the cost of
 * goods sold, where unit cost is the average RECEIPT cost of each product.
 *
 * @param from inclusive start of the reporting range
 * @param to inclusive end of the reporting range
 * @param revenue total revenue of completed orders; never {@code null}
 * @param costOfGoodsSold estimated cost of the goods sold; never {@code null}
 * @param grossProfit {@code revenue − costOfGoodsSold}; never {@code null}
 */
@Schema(description = "Gross profit report over a date range.")
public record ProfitDTO(
    @Schema(description = "Inclusive start of the reporting range.", example = "2026-06-01")
        LocalDate from,
    @Schema(description = "Inclusive end of the reporting range.", example = "2026-06-30")
        LocalDate to,
    @Schema(description = "Total revenue of completed orders (BR-A01).", example = "55000000")
        BigDecimal revenue,
    @Schema(description = "Estimated cost of the goods sold.", example = "35000000")
        BigDecimal costOfGoodsSold,
    @Schema(description = "revenue minus costOfGoodsSold.", example = "20000000")
        BigDecimal grossProfit) {}
