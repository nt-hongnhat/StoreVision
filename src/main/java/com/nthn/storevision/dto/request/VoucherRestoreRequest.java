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
  @AssertFalse(message = "{VCM006}")
  private boolean deleted;
}
