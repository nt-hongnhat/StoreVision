/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: LoyaltyTier.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.constant;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the customer loyalty tiers, their earning rates and qualification thresholds. The tier
 * is derived from the customer's accumulated total spending and never resets (BR-C04).
 */
@Getter
@RequiredArgsConstructor
public enum LoyaltyTier {
  /** Entry tier: total spending below 5.000.000 VND, earning 1 point per 10.000 VND. */
  BRONZE(new BigDecimal("1.0"), BigDecimal.ZERO),
  /** Middle tier: total spending from 5.000.000 VND, earning 1,5 points per 10.000 VND. */
  SILVER(new BigDecimal("1.5"), new BigDecimal("5000000")),
  /** Top tier: total spending from 20.000.000 VND, earning 2 points per 10.000 VND. */
  GOLD(new BigDecimal("2.0"), new BigDecimal("20000000"));

  /** Amount of spending (VND) that earns one batch of {@code pointsPerUnit} points. */
  private static final BigDecimal POINTS_UNIT = BigDecimal.valueOf(10_000);

  private final BigDecimal pointsPerUnit;
  private final BigDecimal minSpending;

  /**
   * Resolves the highest tier whose spending threshold is reached by the given total.
   *
   * @param totalSpending the customer's accumulated spending; must not be {@code null}
   * @return the qualifying {@link LoyaltyTier}; never {@code null}
   */
  public static LoyaltyTier fromTotalSpending(final BigDecimal totalSpending) {
    if (0 <= totalSpending.compareTo(GOLD.minSpending)) {
      return GOLD;
    }
    if (0 <= totalSpending.compareTo(SILVER.minSpending)) {
      return SILVER;
    }
    return BRONZE;
  }

  /**
   * Computes the loyalty points earned for an order amount at this tier's rate, rounded down
   * (floor) as specified in the loyalty design.
   *
   * @param orderAmount the completed order amount; must not be {@code null}
   * @return the number of points earned; never negative
   */
  public long calculatePoints(final BigDecimal orderAmount) {
    return orderAmount
        .multiply(pointsPerUnit)
        .divide(POINTS_UNIT, 0, RoundingMode.FLOOR)
        .longValueExact();
  }
}
