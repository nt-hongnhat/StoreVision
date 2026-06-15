/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemCreationRequest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A single line item inside an {@link OrderCreationRequest}. The unit price is intentionally
 * absent: it is locked server-side from the product's current price at creation time (BR-O04).
 */
@Schema(description = "A single requested line item: product and quantity.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemCreationRequest {

  /** The product being ordered. */
  @Schema(description = "Identifier of the product being ordered.", example = "1")
  @NotNull(message = "{OIM002}")
  private Long productId;

  /** Quantity ordered; must be at least 1. */
  @Schema(description = "Quantity ordered; must be at least 1.", example = "2")
  @NotNull(message = "{OIM003}")
  @Min(value = 1, message = "{OIM004}")
  private Integer quantity;
}
