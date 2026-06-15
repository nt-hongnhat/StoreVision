/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: OrderDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:51:59
 */

package com.nthn.storevision.dto.order;

import com.nthn.storevision.constant.order.OrderChannel;
import com.nthn.storevision.constant.order.OrderStatus;
import com.nthn.storevision.dto.order_item.OrderItemDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for Order. Used to transfer order data between layers, often for API
 * requests and responses.
 */
@Schema(description = "Order details, including its items, totals, and applied discounts/taxes.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
  @Schema(
      description = "Auto-assigned primary key.",
      example = "1",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @Schema(description = "Date and time the order was placed.", example = "2026-06-15T10:30:00")
  @NotNull(message = "{order.orderDate.required}")
  private LocalDateTime orderDate;

  @Schema(description = "Total amount charged for the order.", example = "550000")
  @NotNull(message = "{order.totalAmount.required}")
  private BigDecimal totalAmount;

  @Schema(description = "Current lifecycle status of the order.", example = "PENDING")
  @NotNull(message = "{order.status.required}")
  private OrderStatus status;

  @Schema(description = "Identifier of the customer placing the order.", example = "1")
  @NotNull(message = "{order.customerId.required}")
  private Long customerId;

  /** Response-only: line items belonging to this order; may be {@code null} on requests. */
  @Schema(
      description = "Line items belonging to this order; may be null on requests.",
      accessMode = Schema.AccessMode.READ_ONLY)
  private List<OrderItemDTO> orderItems;

  /** Response-only: sales channel the order originates from (POS or ONLINE). */
  @Schema(
      description = "Sales channel the order originates from (POS or ONLINE).",
      example = "POS",
      accessMode = Schema.AccessMode.READ_ONLY)
  private OrderChannel channel;

  /** Response-only: discount granted by the applied voucher; zero when no voucher was used. */
  @Schema(
      description = "Discount granted by the applied voucher; zero when no voucher was used.",
      example = "50000",
      accessMode = Schema.AccessMode.READ_ONLY)
  private BigDecimal discountAmount;

  /** Response-only: optional note attached to the order; may be {@code null}. */
  @Schema(
      description = "Optional note attached to the order; may be null.",
      example = "Customer requested gift wrap",
      accessMode = Schema.AccessMode.READ_ONLY)
  private String note;

  /** Response-only: sum of all item amounts before discount and tax (BR-O03). */
  @Schema(
      description = "Sum of all item amounts before discount and tax (BR-O03).",
      example = "600000",
      accessMode = Schema.AccessMode.READ_ONLY)
  private BigDecimal subtotal;

  /** Response-only: tax applied to the order. */
  @Schema(
      description = "Tax applied to the order.",
      example = "0",
      accessMode = Schema.AccessMode.READ_ONLY)
  private BigDecimal taxAmount;

  /** Response-only: ID of the applied voucher; {@code null} when no voucher was used. */
  @Schema(
      description = "ID of the applied voucher; null when no voucher was used.",
      example = "5",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Long voucherId;
}
