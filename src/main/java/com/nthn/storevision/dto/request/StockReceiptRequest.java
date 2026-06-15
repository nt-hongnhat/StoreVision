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

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request to record a stock receipt for a product (BR-I02).")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockReceiptRequest {

  @Schema(description = "Optional note for the receipt.", example = "Received from supplier ABC")
  private String note;

  /** The product to restock. */
  @Schema(description = "Identifier of the product to restock.", example = "1")
  @NotNull(message = "{INM001}")
  private Long productId;

  /** BR-I02: quantity must be at least 1. */
  @Schema(description = "Quantity received; must be at least 1 (BR-I02).", example = "20")
  @NotNull(message = "{INM002}")
  @Min(value = 1, message = "{INM003}")
  private Integer quantity;

  /** Purchase cost per unit; optional for RECEIPT movements. */
  @Schema(description = "Purchase cost per unit; optional for RECEIPT movements.", example = "150000")
  @DecimalMin(value = "0", message = "{INM004}")
  private BigDecimal unitCost;
}
