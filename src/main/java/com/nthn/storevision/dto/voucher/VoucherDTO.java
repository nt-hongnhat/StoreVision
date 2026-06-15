/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: VoucherDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 22:48:03
 */

package com.nthn.storevision.dto.voucher;

import com.nthn.storevision.constant.voucher.DiscountType;
import com.nthn.storevision.dto.response.HasIdentifier;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.*;

/**
 * Data Transfer Object for {@link com.nthn.storevision.model.Voucher}. Used to carry validated
 * voucher data between domains — e.g., from the voucher validation service to the order creation
 * flow.
 */
@Schema(
    description = "Voucher details, including its discount rule, validity window and usage limits.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class VoucherDTO implements HasIdentifier<Long> {

  /** Whether the voucher is currently activated (BR-V02). */
  @Schema(description = "Whether the voucher is currently activated (BR-V02).", example = "true")
  private boolean active;

  /** Unique code customers enter to apply the voucher. */
  @Schema(description = "Unique code customers enter to apply the voucher.", example = "SUMMER10")
  private String code;

  /** How {@code discountValue} is interpreted: percentage or fixed amount. */
  @Schema(
      description = "How discountValue is interpreted: percentage or fixed amount.",
      example = "PERCENTAGE")
  private DiscountType discountType;

  /** Percentage (for PERCENTAGE) or amount in VND (for FIXED_AMOUNT); always positive. */
  @Schema(
      description =
          "Percentage (for PERCENTAGE) or amount in VND (for FIXED_AMOUNT); always positive.",
      example = "10")
  private BigDecimal discountValue;

  /** Date the voucher stops being valid (BR-V01). */
  @Schema(description = "Date the voucher stops being valid (BR-V01).", example = "2026-12-31")
  private LocalDate expiryDate;

  @Schema(
      description = "Auto-assigned primary key.",
      example = "1",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  /** Cap for percentage discounts; {@code null} when uncapped. */
  @Schema(description = "Cap for percentage discounts; null when uncapped.", example = "50000")
  private BigDecimal maxDiscountAmount;

  /** Total number of times the voucher may be used (BR-V03). */
  @Schema(description = "Total number of times the voucher may be used (BR-V03).", example = "100")
  private Integer maxUsage;

  /** Minimum order amount required to apply the voucher; {@code null} when none (BR-V04). */
  @Schema(
      description = "Minimum order amount required to apply the voucher; null when none (BR-V04).",
      example = "200000")
  private BigDecimal minOrderAmount;

  /** Date the voucher becomes valid. */
  @Schema(description = "Date the voucher becomes valid.", example = "2026-06-01")
  private LocalDate startDate;

  /** Number of times the voucher has already been used. */
  @Schema(
      description = "Number of times the voucher has already been used.",
      example = "0",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Integer usageCount;
}
