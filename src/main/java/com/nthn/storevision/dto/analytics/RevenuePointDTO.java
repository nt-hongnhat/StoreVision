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

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * A single bucket of the revenue report — only COMPLETED orders are counted (BR-A01).
 *
 * @param period start of the time bucket (day, week or month)
 * @param revenue total revenue of the bucket; never {@code null}
 * @param orderCount number of completed orders in the bucket; never {@code null}
 */
public record RevenuePointDTO(LocalDateTime period, BigDecimal revenue, Long orderCount) {}
