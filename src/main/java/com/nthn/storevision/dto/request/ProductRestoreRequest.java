/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductRestoreRequest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14
 */

package com.nthn.storevision.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertFalse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request body for PATCH /products/'{'id'}'. Clears the soft-delete markers of a previously
 * deleted product.
 */
@Schema(description = "Restores a previously soft-deleted product.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRestoreRequest {

  /**
   * Must be {@code false}; restores the product by clearing its soft-delete markers.
   * {@code true} is rejected — use {@code DELETE /products/{id}} to soft-delete instead.
   */
  @Schema(
      description =
          "Must be false; restores the product by clearing its soft-delete markers. true is "
              + "rejected — use DELETE /products/{id} to soft-delete instead.",
      example = "false")
  @AssertFalse(message = "{PRM008}")
  private boolean deleted;
}
