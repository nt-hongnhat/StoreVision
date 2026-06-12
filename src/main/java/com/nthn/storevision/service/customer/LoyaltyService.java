/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: LoyaltyService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 21:57:29
 */

package com.nthn.storevision.service.customer;

import com.nthn.storevision.dto.LoyaltyDetailsDTO;
import com.nthn.storevision.dto.VoucherDTO;
import java.math.BigDecimal;
import org.jspecify.annotations.NonNull;

/**
 * Service interface for the customer loyalty program (module 3.4). Awards points when an order is
 * completed, accumulates total spending, upgrades the customer's tier when a threshold is reached
 * (BR-C04), and redeems points for discount vouchers. Every transaction is recorded in the
 * immutable point history (BR-C05).
 */
public interface LoyaltyService {

  /**
   * Awards loyalty points to a customer for a completed order. The points are computed from the
   * customer's current tier rate, the total spending is increased by the order amount, the tier is
   * upgraded when eligible (never downgraded — BR-C04), and an {@code EARN} entry is appended to
   * the point history.
   *
   * @param customerId the customer earning the points; must not be {@code null}
   * @param orderAmount the completed order's total amount; must not be {@code null}
   * @param referenceOrderId the completed order generating the points; must not be {@code null}
   * @throws com.nthn.storevision.exception.EntityNotFoundException if the customer does not exist
   */
  void addPoints(
      @NonNull Long customerId, @NonNull BigDecimal orderAmount, @NonNull Long referenceOrderId);

  /**
   * Retrieves a customer's loyalty overview: current tier, point balance, total spending and the
   * full point transaction history (most recent first).
   *
   * @param customerId the customer to query; must not be {@code null}
   * @return the loyalty overview as a {@link LoyaltyDetailsDTO}; never {@code null}
   * @throws com.nthn.storevision.exception.EntityNotFoundException if the customer does not exist
   */
  LoyaltyDetailsDTO getLoyaltyDetails(@NonNull Long customerId);

  /**
   * Redeems loyalty points for a single-use fixed-amount discount voucher. The point balance is
   * decreased by {@code points} — it can never go negative, redemptions exceeding the balance are
   * rejected (BR-C03) — and a {@code REDEEM} entry referencing the minted voucher is appended to
   * the point history. The tier and total spending are not affected (BR-C04).
   *
   * @param customerId the customer redeeming the points; must not be {@code null}
   * @param points the number of points to redeem; must not be {@code null} and must be positive
   * @return the minted voucher as a {@link VoucherDTO}; never {@code null}
   * @throws com.nthn.storevision.exception.EntityNotFoundException if the customer does not exist
   * @throws com.nthn.storevision.exception.InsufficientLoyaltyPointsException if {@code points}
   *     exceeds the customer's balance (BR-C03)
   */
  VoucherDTO redeemPoints(@NonNull Long customerId, @NonNull Long points);
}
