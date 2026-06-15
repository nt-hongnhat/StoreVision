/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherRetrievalService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 16:43:14
 */

package com.nthn.storevision.service.voucher;

import com.nthn.storevision.dto.voucher.VoucherDTO;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.service.generic.Readable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for retrieving {@link VoucherEntity} instances.
 */
public interface VoucherRetrievalService extends Readable<VoucherDTO, VoucherEntity> {

  /**
   * Retrieves a paginated list of active vouchers.
   *
   * @param pageable the pagination information
   * @return a page of VoucherDTOs
   */
  Page<VoucherDTO> findVouchersPage(Pageable pageable);
}