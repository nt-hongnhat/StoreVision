/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderCreationRequest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 20:55:20
 */

package com.nthn.storevision.dto.request;

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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreationRequest {

  /** The customer placing the order. */
  @NotNull(message = "{ORM002}")
  private Long customerId;

  /** BR-O05: an order must contain at least one item. */
  @Valid
  @NotEmpty(message = "{ORM003}")
  private List<OrderItemCreationRequest> items;
}
