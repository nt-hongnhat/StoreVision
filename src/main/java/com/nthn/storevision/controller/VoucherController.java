/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherController.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 16:43:14
 */

package com.nthn.storevision.controller;

import com.nthn.storevision.dto.voucher.VoucherDTO;
import com.nthn.storevision.dto.request.VoucherRestoreRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

/**
 * API contract for voucher management endpoints.
 *
 * @see com.nthn.storevision.controller.v1.VoucherControllerV1
 */
public interface VoucherController {

  /**
   * Creates a new voucher.
   *
   * @param dto the voucher data to persist
   * @return the created voucher
   */
  VoucherDTO createVoucher(@Valid VoucherDTO dto);

  /**
   * Retrieves one page of active vouchers.
   *
   * @param page zero-based page index
   * @param size page size
   * @return the requested page of vouchers
   */
  Page<VoucherDTO> getAllVouchers(int page, int size);

  /**
   * Retrieves a voucher by ID.
   *
   * @param id the ID of the voucher to retrieve
   * @return the matching voucher
   */
  VoucherDTO getVoucherById(Long id);

  /**
   * Updates an existing voucher.
   *
   * @param id the ID of the voucher to update
   * @param dto the updated voucher data
   * @return the updated voucher
   */
  VoucherDTO updateVoucher(Long id, @Valid VoucherDTO dto);

  /**
   * Soft-deletes a voucher by ID.
   *
   * @param id the ID of the voucher to delete
   */
  void deleteVoucher(Long id);

  /**
   * Restores a previously soft-deleted voucher by clearing its soft-delete markers.
   *
   * @param id the ID of the voucher to restore
   * @param request the restore request; {@code deleted} must be {@code false} — use {@code DELETE
   *     /vouchers/{id}} to soft-delete a voucher instead
   * @return the restored voucher
   */
  VoucherDTO restoreVoucher(Long id, @Valid VoucherRestoreRequest request);
}
