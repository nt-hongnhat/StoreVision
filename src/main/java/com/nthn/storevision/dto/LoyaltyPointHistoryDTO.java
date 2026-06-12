/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: LoyaltyPointHistoryDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 21:56:33
 */

package com.nthn.storevision.dto;

import com.nthn.storevision.constant.LoyaltyPointType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for a single loyalty point transaction. Response-only — the history is
 * immutable (BR-C05) and written exclusively by the loyalty flow.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoyaltyPointHistoryDTO {

  /** Point balance right after this transaction. */
  private Long balanceAfter;

  /** The customer this transaction belongs to. */
  private Long customerId;

  private Long id;

  /** Number of points moved; always positive, direction is encoded in {@code type}. */
  private Long points;

  /** Order that earned the points; {@code null} for REDEEM transactions. */
  private Long referenceOrderId;

  /** Voucher obtained by redemption; {@code null} for EARN transactions. */
  private Long referenceVoucherId;

  /** When the transaction was recorded. */
  private LocalDateTime transactionDate;

  /** Direction of the transaction: EARN or REDEEM. */
  private LoyaltyPointType type;
}
