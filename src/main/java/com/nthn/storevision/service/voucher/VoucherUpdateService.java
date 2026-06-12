/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherUpdateService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.service.voucher;

import org.jspecify.annotations.NonNull;

/** Service interface for updating voucher state. */
@FunctionalInterface
public interface VoucherUpdateService {

  /**
   * Increments the usage count of a voucher by one. Called when an order using the voucher is
   * completed (step S7 of the completion flow) — never at order creation time.
   *
   * @param voucherId the voucher to update; must not be {@code null}
   * @throws com.nthn.storevision.exception.EntityNotFoundException if the voucher does not exist
   */
  void incrementUsage(@NonNull Long voucherId);
}
