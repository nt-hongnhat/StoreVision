/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: StockMovementDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 17:15:15
 */

package com.nthn.storevision.dto;

import com.nthn.storevision.constant.StockMovementType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Full movement record returned by GET /inventory/movements/{productId}. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovementDTO {

  private Long id;
  private LocalDateTime movementDate;
  private String note;

  /** Email of the employee who performed this movement (BR-I01). */
  private String performedBy;

  private Long productId;
  private String productName;
  private Integer quantity;

  /** Populated only for ISSUE movements. */
  private Long referenceOrderId;

  private StockMovementType type;
  private BigDecimal unitCost;
}
