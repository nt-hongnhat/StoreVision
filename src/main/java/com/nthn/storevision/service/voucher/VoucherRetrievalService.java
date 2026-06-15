/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: VoucherRetrievalService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 22:38:31
 */

package com.nthn.storevision.service.voucher;

import com.nthn.storevision.dto.voucher.VoucherDTO;
import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.service.generic.Readable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/** Service interface for retrieving {@link VoucherEntity} instances. */
public interface VoucherRetrievalService extends Readable<VoucherEntity, Long> {

  /**
   * Retrieves a voucher by its identifier and maps it to {@link VoucherDTO}.
   *
   * @param id the identifier of the voucher to retrieve; must not be {@code null}
   * @return the {@link VoucherDTO} with the specified identifier; never {@code null}
   * @throws com.nthn.storevision.exception.EntityNotFoundException if no voucher matches the given
   *     id
   */
  VoucherDTO findById(Long id);

  /**
   * Retrieves a paginated list of active vouchers.
   *
   * @param pageable the pagination information; must not be {@code null}
   * @return a page of {@link VoucherDTO}s; never {@code null}
   */
  Page<VoucherDTO> findVouchersPage(Pageable pageable);
}
