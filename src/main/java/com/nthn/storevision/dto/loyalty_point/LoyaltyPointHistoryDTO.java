/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: LoyaltyPointHistoryDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 22:45:03
 */

package com.nthn.storevision.dto.loyalty_point;

import com.nthn.storevision.constant.loyalty_point.LoyaltyPointType;
import com.nthn.storevision.dto.response.HasIdentifier;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

import lombok.*;

/**
 * Data Transfer Object for a single loyalty point transaction. Response-only — the history is
 * immutable (BR-C05) and written exclusively by the loyalty flow.
 */
@Schema(description = "A single loyalty point transaction; immutable once recorded (BR-C05).")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoyaltyPointHistoryDTO implements HasIdentifier<Long> {

  /** Point balance right after this transaction. */
  @Schema(description = "Point balance right after this transaction.", example = "1200")
  private Long balanceAfter;

  /** The customer this transaction belongs to. */
  @Schema(description = "Identifier of the customer this transaction belongs to.", example = "1")
  private Long customerId;

  @Schema(description = "Auto-assigned primary key.", example = "10")
  private Long id;

  /** Number of points moved; always positive, direction is encoded in {@code type}. */
  @Schema(
      description = "Number of points moved; always positive, direction is encoded in type.",
      example = "50")
  private Long points;

  /** Order that earned the points; {@code null} for REDEEM transactions. */
  @Schema(
      description = "Order that earned the points; null for REDEEM transactions.",
      example = "100")
  private Long referenceOrderId;

  /** Voucher obtained by redemption; {@code null} for EARN transactions. */
  @Schema(
      description = "Voucher obtained by redemption; null for EARN transactions.",
      example = "5")
  private Long referenceVoucherId;

  /** When the transaction was recorded. */
  @Schema(description = "When the transaction was recorded.", example = "2026-06-10T14:30:00")
  private LocalDateTime transactionDate;

  /** Direction of the transaction: EARN or REDEEM. */
  @Schema(description = "Direction of the transaction: EARN or REDEEM.", example = "EARN")
  private LoyaltyPointType type;
}
