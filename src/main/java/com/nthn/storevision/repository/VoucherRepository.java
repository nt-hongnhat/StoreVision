/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:08:47
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.repository.generic.ReadRepository;
import com.nthn.storevision.repository.generic.SaveRepository;
import java.util.Optional;

/**
 * Repository interface for managing {@link VoucherEntity} instances. Defines read and save
 * operations for vouchers — vouchers are never hard-deleted from business flows.
 */
public interface VoucherRepository
    extends ReadRepository<VoucherEntity, Long>, SaveRepository<VoucherEntity> {

  /**
   * Finds a voucher by its unique code.
   *
   * @param code the voucher code to look up; must not be {@code null}
   * @return an {@link Optional} containing the matching {@link VoucherEntity}, or empty if no
   *     voucher carries the given code
   */
  Optional<VoucherEntity> findByCode(String code);
   * @return {@code true} when the count was incremented; {@code false} when the voucher does not
}
