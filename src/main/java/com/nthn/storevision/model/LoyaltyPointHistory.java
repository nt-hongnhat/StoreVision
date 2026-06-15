/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: LoyaltyPointHistory.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:51:59
 */

package com.nthn.storevision.model;

import com.nthn.storevision.constant.loyalty_point.LoyaltyPointType;
import java.time.LocalDateTime;

/**
 * Represents an immutable loyalty point transaction in the StoreVision domain. Once recorded, a
 * history entry cannot be deleted (BR-C05).
 *
 * <p>Unlike auditable entities, {@code LoyaltyPointHistory} does not extend {@link CustomAuditable}
 * — the {@link #getTransactionDate()} field serves as the audit trail and is set at creation time.
 */
public interface LoyaltyPointHistory {

  /**
   * Gets the point balance of the customer right after this transaction.
   *
   * @return the balance as a non-negative {@link Long}; never {@code null}.
   */
  Long getBalanceAfter();

  /**
   * Sets the point balance right after this transaction.
   *
   * @param balanceAfter the balance to set; must not be {@code null} and must be non-negative.
   * @return the updated {@code LoyaltyPointHistory} instance.
   */
  LoyaltyPointHistory setBalanceAfter(Long balanceAfter);

  /**
   * Gets the customer this transaction belongs to.
   *
   * @return the {@link Customer} instance; never {@code null}.
   */
  Customer getCustomer();

  /**
   * Sets the customer this transaction belongs to.
   *
   * @param customer the customer to set; must not be {@code null}.
   * @return the updated {@code LoyaltyPointHistory} instance.
   */
  LoyaltyPointHistory setCustomer(Customer customer);

  /**
   * Gets the unique identifier of this transaction.
   *
   * @return the id as a {@link Long}.
   */
  Long getId();

  /**
   * Sets the unique identifier of this transaction.
   *
   * @param id the id to set.
   * @return the updated {@code LoyaltyPointHistory} instance.
   */
  LoyaltyPointHistory setId(Long id);

  /**
   * Gets the number of points moved by this transaction. Always positive; direction is encoded in
   * {@link #getType()}.
   *
   * @return the points as a positive {@link Long}; never {@code null}.
   */
  Long getPoints();

  /**
   * Sets the number of points moved by this transaction.
   *
   * @param points a positive number of points; must not be {@code null}.
   * @return the updated {@code LoyaltyPointHistory} instance.
   */
  LoyaltyPointHistory setPoints(Long points);

  /**
   * Gets the FK reference to the order that earned the points. Populated only for {@link
   * LoyaltyPointType#EARN} transactions.
   *
   * @return the order id as a {@link Long}, or {@code null} for REDEEM transactions.
   */
  Long getReferenceOrderId();

  /**
   * Sets the FK reference to the order that earned the points.
   *
   * @param referenceOrderId the order id; may be {@code null} for REDEEM transactions.
   * @return the updated {@code LoyaltyPointHistory} instance.
   */
  LoyaltyPointHistory setReferenceOrderId(Long referenceOrderId);

  /**
   * Gets the FK reference to the voucher obtained by redeeming points. Populated only for {@link
   * LoyaltyPointType#REDEEM} transactions.
   *
   * @return the voucher id as a {@link Long}, or {@code null} for EARN transactions.
   */
  Long getReferenceVoucherId();

  /**
   * Sets the FK reference to the voucher obtained by redeeming points.
   *
   * @param referenceVoucherId the voucher id; may be {@code null} for EARN transactions.
   * @return the updated {@code LoyaltyPointHistory} instance.
   */
  LoyaltyPointHistory setReferenceVoucherId(Long referenceVoucherId);

  /**
   * Gets the date and time this transaction was recorded.
   *
   * @return the transaction date as a {@link LocalDateTime}; never {@code null}.
   */
  LocalDateTime getTransactionDate();

  /**
   * Sets the date and time this transaction was recorded.
   *
   * @param transactionDate the transaction date to set; must not be {@code null}.
   * @return the updated {@code LoyaltyPointHistory} instance.
   */
  LoyaltyPointHistory setTransactionDate(LocalDateTime transactionDate);

  /**
   * Gets the direction of this transaction.
   *
   * @return the {@link LoyaltyPointType} classifying this transaction; never {@code null}.
   */
  LoyaltyPointType getType();

  /**
   * Sets the direction of this transaction.
   *
   * @param type the {@link LoyaltyPointType} to set; must not be {@code null}.
   * @return the updated {@code LoyaltyPointHistory} instance.
   */
  LoyaltyPointHistory setType(LoyaltyPointType type);
}
