/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: LoyaltyDetailsDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.dto;

import com.nthn.storevision.constant.LoyaltyTier;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for a customer's loyalty overview: current tier, point balance, accumulated
 * spending and the full transaction history. Backs GET /customers/'{'id'}'/loyalty.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoyaltyDetailsDTO {

  /** The customer this overview belongs to. */
  private Long customerId;

  /** Point transactions, most recent first; never {@code null} (BR-C05 — immutable history). */
  private List<LoyaltyPointHistoryDTO> histories;

  /** Current point balance; never negative (BR-C03). */
  private Long loyaltyPoints;

  /** Current tier, derived from total spending (BR-C04). */
  private LoyaltyTier loyaltyTier;

  /** Accumulated lifetime spending; never resets (BR-C04). */
  private BigDecimal totalSpending;
}
