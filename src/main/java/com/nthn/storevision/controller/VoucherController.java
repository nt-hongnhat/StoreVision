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

import com.nthn.storevision.dto.VoucherDTO;
import com.nthn.storevision.dto.response.PageDTO;
import jakarta.validation.Valid;

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
  PageDTO<VoucherDTO> getAllVouchers(int page, int size);

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
   * Restores a soft-deleted voucher by ID.
   *
   * @param id the ID of the voucher to restore
   * @return the restored voucher
   */
  VoucherDTO restoreVoucher(Long id);
}
