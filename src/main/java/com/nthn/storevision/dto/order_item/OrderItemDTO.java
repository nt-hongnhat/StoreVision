/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: OrderItemDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 22:43:03
 */

package com.nthn.storevision.dto.order_item;

import com.nthn.storevision.dto.response.HasIdentifier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/**
 * Data Transfer Object for OrderItem. Used to transfer order item data between layers, often for
 * API requests and responses.
 */
@Schema(description = "A single line item of an order: product, quantity and locked unit price.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderItemDTO implements HasIdentifier<Long> {
  @Schema(
      description = "Auto-assigned primary key.",
      example = "1",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @Schema(description = "Identifier of the order this item belongs to.", example = "1")
  @NotNull(message = "{orderitem.orderId.required}")
  private Long orderId;

  @Schema(description = "Identifier of the ordered product.", example = "1")
  @NotNull(message = "{orderitem.productId.required}")
  private Long productId;

  @Schema(description = "Quantity ordered; must be at least 1.", example = "2")
  @NotNull(message = "{orderitem.quantity.required}")
  @Min(value = 1, message = "{orderitem.quantity.min}")
  private Integer quantity;

  @Schema(
      description = "Unit price locked in at order time; must not be negative.",
      example = "299000")
  @NotNull(message = "{orderitem.unitPrice.required}")
  @Min(value = 0, message = "{orderitem.unitPrice.min}")
  private BigDecimal unitPrice;
}
