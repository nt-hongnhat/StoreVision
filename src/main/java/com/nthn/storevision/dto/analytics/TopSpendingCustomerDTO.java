/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: TopSpendingCustomerDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:51:59
 */

package com.nthn.storevision.dto.analytics;

import com.nthn.storevision.constant.loyalty_point.LoyaltyTier;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/**
 * One row of the top-spending customers report, ranked by accumulated lifetime spending (BR-C04).
 *
 * @param customerId the customer's identifier
 * @param name the customer's full name
 * @param email the customer's email
 * @param loyaltyTier the customer's current loyalty tier
 * @param totalSpending accumulated lifetime spending; never {@code null}
 */
@Schema(description = "One row of the top-spending customers report.")
public record TopSpendingCustomerDTO(
    @Schema(description = "The customer's identifier.", example = "1") Long customerId,
    @Schema(description = "The customer's full name.", example = "Nguyen Van A") String name,
    @Schema(description = "The customer's email.", example = "customer@example.com") String email,
    @Schema(description = "The customer's current loyalty tier.", example = "SILVER")
        LoyaltyTier loyaltyTier,
    @Schema(description = "Accumulated lifetime spending (BR-C04).", example = "7500000")
        BigDecimal totalSpending) {}
