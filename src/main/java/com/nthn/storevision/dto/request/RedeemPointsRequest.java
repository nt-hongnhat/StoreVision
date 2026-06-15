/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: RedeemPointsRequest.java
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

/** Request body for POST /customers/'{'id'}'/loyalty/redeem. */
@Schema(description = "Request to redeem loyalty points for a customer (BR-C03).")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RedeemPointsRequest {

  /** Number of points to redeem; must not exceed the customer's balance (BR-C03). */
  @Schema(
      description = "Number of points to redeem; must not exceed the customer's balance (BR-C03).",
      example = "100")
  @NotNull(message = "{CSM005}")
  @Min(value = 1, message = "{CSM006}")
  private Long points;
}
