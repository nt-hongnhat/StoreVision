/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: LoyaltyDetailsDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:52:00
 */

package com.nthn.storevision.dto.loyalty_point;

import com.nthn.storevision.constant.loyalty_point.LoyaltyTier;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;

import lombok.*;

/**
 * Data Transfer Object for a customer's loyalty overview: current tier, point balance, accumulated
 * spending, and the full transaction history. Backs GET /customers/'{'id'}'/loyalty.
 */
@Schema(
    description =
        "Customer's loyalty program overview: tier, point balance, spending and transaction history.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoyaltyDetailsDTO {

  /** The customer this overview belongs to. */
  @Schema(description = "Identifier of the customer this overview belongs to.", example = "1")
  private Long customerId;

  /** Point transactions, most recent first; never {@code null} (BR-C05 — immutable history). */
  @Schema(
      description =
          "Point transactions, most recent first; never null (BR-C05 — immutable history).")
  private List<LoyaltyPointHistoryDTO> histories;

  /** Current point balance; never negative (BR-C03). */
  @Schema(description = "Current point balance; never negative (BR-C03).", example = "1200")
  private Long loyaltyPoints;

  /** Current tier, derived from total spending (BR-C04). */
  @Schema(description = "Current tier, derived from total spending (BR-C04).", example = "SILVER")
  private LoyaltyTier loyaltyTier;

  /** Accumulated lifetime spending; never resets (BR-C04). */
  @Schema(
      description = "Accumulated lifetime spending; never resets (BR-C04).",
      example = "7500000")
  private BigDecimal totalSpending;
}
