/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: StockMovementDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:55:06
 */

package com.nthn.storevision.dto.stock_movement;

import com.nthn.storevision.constant.stock_movement.StockMovementType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Full movement record returned by GET /inventory/{productId}/movements. */
@Schema(description = "A single stock movement record for a product.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovementDTO {

  @Schema(description = "Auto-assigned primary key.", example = "1")
  private Long id;

  @Schema(description = "Date and time the movement was recorded.", example = "2026-06-10T09:15:00")
  private LocalDateTime movementDate;

  @Schema(
      description = "Optional note attached to the movement.",
      example = "Received from supplier ABC")
  private String note;

  /** Email of the employee who performed this movement (BR-I01). */
  @Schema(
      description = "Email of the employee who performed this movement (BR-I01).",
      example = "admin@example.com")
  private String performedBy;

  @Schema(description = "Identifier of the affected product.", example = "1")
  private Long productId;

  @Schema(description = "Display name of the affected product.", example = "Wireless Mouse")
  private String productName;

  @Schema(description = "Quantity moved.", example = "20")
  private Integer quantity;

  /** Populated only for ISSUE movements. */
  @Schema(
      description = "Order that triggered this movement; populated only for ISSUE movements.",
      example = "100")
  private Long referenceOrderId;

  @Schema(description = "Type of stock movement.", example = "RECEIPT")
  private StockMovementType type;

  @Schema(description = "Cost per unit at the time of this movement.", example = "150000")
  private BigDecimal unitCost;
}
