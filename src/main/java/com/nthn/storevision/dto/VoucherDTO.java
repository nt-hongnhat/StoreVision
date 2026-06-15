/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: VoucherDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:39:29
 */

package com.nthn.storevision.dto;

import com.nthn.storevision.constant.DiscountType;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for {@link com.nthn.storevision.model.Voucher}. Used to carry validated
 * voucher data between domains — e.g., from the voucher validation service to the order creation
 * flow.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherDTO {

  /** Whether the voucher is currently activated (BR-V02). */
  private boolean active;

  /** Unique code customers enter to apply the voucher. */
  private String code;

  /** How {@code discountValue} is interpreted: percentage or fixed amount. */
  private DiscountType discountType;

  /** Percentage (for PERCENTAGE) or amount in VND (for FIXED_AMOUNT); always positive. */
  private BigDecimal discountValue;

  /** Date the voucher stops being valid (BR-V01). */
  private LocalDate expiryDate;

  private Long id;

  /** Cap for percentage discounts; {@code null} when uncapped. */
  private BigDecimal maxDiscountAmount;

  /** Total number of times the voucher may be used (BR-V03). */
  private Integer maxUsage;

  /** Minimum order amount required to apply the voucher; {@code null} when none (BR-V04). */
  private BigDecimal minOrderAmount;

  /** Date the voucher becomes valid. */
  private LocalDate startDate;

  /** Number of times the voucher has already been used. */
  private Integer usageCount;
}
