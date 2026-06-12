/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: StockReceiptRequest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 17:15:15
 */

package com.nthn.storevision.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Request body for POST /inventory/receipts. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockReceiptRequest {

  private String note;

  /** The product to restock. */
  @NotNull(message = "{INM001}")
  private Long productId;

  /** BR-I02: quantity must be at least 1. */
  @NotNull(message = "{INM002}")
  @Min(value = 1, message = "{INM003}")
  private Integer quantity;

  /** Purchase cost per unit; optional for RECEIPT movements. */
  @DecimalMin(value = "0", message = "{INM004}")
  private BigDecimal unitCost;
}
