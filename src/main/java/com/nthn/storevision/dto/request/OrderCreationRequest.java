/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: OrderCreationRequest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:50:33
 */

package com.nthn.storevision.dto.request;

import com.nthn.storevision.constant.order.OrderChannel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request body for POST /orders. The total amount is never accepted from the client — it is always
 * recalculated server-side (BR-O03).
 */
@Schema(description = "Request to create a new order (BR-O03, BR-O05).")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreationRequest {

  /** Sales channel the order originates from (POS or ONLINE). */
  @Schema(description = "Sales channel the order originates from (POS or ONLINE).", example = "POS")
  @NotNull(message = "{ORM005}")
  private OrderChannel channel;

  /** The customer placing the order. */
  @Schema(description = "Identifier of the customer placing the order.", example = "1")
  @NotNull(message = "{ORM002}")
  private Long customerId;

  /** BR-O05: an order must contain at least one item. */
  @Schema(description = "Line items for the order; must contain at least one item (BR-O05).")
  @Valid
  @NotEmpty(message = "{ORM003}")
  private List<OrderItemCreationRequest> items;

  /** Optional note attached to the order; may be {@code null}. */
  @Schema(
      description = "Optional note attached to the order; may be null.",
      example = "Customer requested gift wrap")
  private String note;

  /** Optional voucher code to apply; validated server-side (BR-V01–BR-V04) when present. */
  @Schema(
      description =
          "Optional voucher code to apply; validated server-side (BR-V01-BR-V04) when present.",
      example = "SUMMER10")
  private String voucherCode;
}
