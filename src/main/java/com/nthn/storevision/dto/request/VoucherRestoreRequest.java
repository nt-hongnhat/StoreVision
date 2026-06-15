/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherRestoreRequest.java
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
 * Request body for PATCH /vouchers/'{'id'}'. Clears the soft-delete markers of a previously
 * deleted voucher.
 */
@Schema(description = "Restores a previously soft-deleted voucher.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherRestoreRequest {

  /**
   * Must be {@code false}; restores the voucher by clearing its soft-delete markers.
   * {@code true} is rejected — use {@code DELETE /vouchers/{id}} to soft-delete instead.
   */
  @Schema(
      description =
          "Must be false; restores the voucher by clearing its soft-delete markers. true is "
              + "rejected — use DELETE /vouchers/{id} to soft-delete instead.",
      example = "false")
  @AssertFalse(message = "{VCM006}")
  private boolean deleted;
}
