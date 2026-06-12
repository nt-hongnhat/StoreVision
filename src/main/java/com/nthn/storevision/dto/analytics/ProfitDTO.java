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
public record ProfitDTO(
    LocalDate from,
    LocalDate to,
    BigDecimal revenue,
    BigDecimal costOfGoodsSold,
    BigDecimal grossProfit) {}
