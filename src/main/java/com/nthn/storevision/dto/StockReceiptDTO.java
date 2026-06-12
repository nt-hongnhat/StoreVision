/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: StockReceiptDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 17:15:15
 */

package com.nthn.storevision.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Response for POST /inventory/receipts and items in GET /inventory/receipts. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockReceiptDTO {

  private Long id;
  private LocalDateTime movementDate;
  private String note;

  /** Email of the employee who recorded this receipt (BR-I01). */
  private String performedBy;

  private Long productId;
  private String productName;
  private Integer quantity;
  private BigDecimal unitCost;
}
