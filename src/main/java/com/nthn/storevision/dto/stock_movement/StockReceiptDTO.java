/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: StockReceiptDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 22:47:43
 */

package com.nthn.storevision.dto.stock_movement;

import com.nthn.storevision.dto.response.HasIdentifier;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

/** Response for POST /inventory/receipts and items in GET /inventory/receipts. */
@Schema(description = "A single stock receipt record for a product.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StockReceiptDTO implements HasIdentifier<Long> {

  @Schema(description = "Auto-assigned primary key.", example = "1")
  private Long id;

  @Schema(description = "Date and time the receipt was recorded.", example = "2026-06-10T09:15:00")
  private LocalDateTime movementDate;

  @Schema(
      description = "Optional note attached to the receipt.",
      example = "Received from supplier ABC")
  private String note;

  /** Email of the employee who recorded this receipt (BR-I01). */
  @Schema(
      description = "Email of the employee who recorded this receipt (BR-I01).",
      example = "admin@example.com")
  private String performedBy;

  @Schema(description = "Identifier of the restocked product.", example = "1")
  private Long productId;

  @Schema(description = "Display name of the restocked product.", example = "Wireless Mouse")
  private String productName;

  @Schema(description = "Quantity received.", example = "20")
  private Integer quantity;

  @Schema(description = "Purchase cost per unit.", example = "150000")
  private BigDecimal unitCost;
}
