/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: InventoryDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:48:52
 */

package com.nthn.storevision.dto.inventory;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

import lombok.*;

/**
 * Stock-level summary for a single product. Used by GET /inventory (including the {@code
 * lowStock=true} filter) and GET /inventory/{productId}.
 */
@Schema(description = "Stock-level summary for a single product.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InventoryDTO {

  @Schema(description = "Category the product belongs to.", example = "Electronics")
  private String category;

  @Schema(description = "Current selling price of the product.", example = "299000")
  private BigDecimal price;

  @Schema(description = "Identifier of the product.", example = "1")
  private Long productId;

  @Schema(description = "Display name of the product.", example = "Wireless Mouse")
  private String productName;

  @Schema(description = "Stock-keeping unit code of the product.", example = "WM-001")
  private String sku;

  @Schema(description = "Current units in stock.", example = "50")
  private Integer stockQuantity;
}
