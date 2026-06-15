/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: OrderStatusUpdateRequest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:51:21
 */

package com.nthn.storevision.dto.request;

import com.nthn.storevision.constant.order.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request body for PATCH /orders/'{'id'}'. Drives the order lifecycle transition out of {@code
 * PENDING} (BR-O02).
 */
@Schema(description = "Request to transition an order's status out of PENDING (BR-O02).")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusUpdateRequest {

  /** Target status; only {@code COMPLETED} and {@code CANCELLED} are valid transitions (BR-O02). */
  @Schema(
      description = "Target status; only COMPLETED and CANCELLED are valid transitions (BR-O02).",
      example = "COMPLETED")
  @NotNull(message = "{ORM007}")
  private OrderStatus status;
}
