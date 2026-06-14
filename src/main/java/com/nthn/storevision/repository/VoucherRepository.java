/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 15:44:37
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.repository.generic.DeleteRepository;
import com.nthn.storevision.repository.generic.ReadRepository;
import com.nthn.storevision.repository.generic.SaveRepository;
import java.util.Optional;

/**
 * Repository interface for managing {@link VoucherEntity} instances. Defines read, save, and delete
 * operations for vouchers.
 */
public interface VoucherRepository
    extends ReadRepository<VoucherEntity, Long>,
        SaveRepository<VoucherEntity>,
        DeleteRepository<Long> {

  /**
   * Finds a voucher by its unique code.
   *
   * @param code the voucher code to look up; must not be {@code null}
   * @return an {@link Optional} containing the matching {@link VoucherEntity}, or empty if no
   *     voucher carries the given code
   */
  Optional<VoucherEntity> findByCode(String code);

  /**
   * BR-V03: atomically increments a voucher's usage count only while it is still below the usage
   * limit — concurrent completions can never push the count past the maximum.
   *
   * @param id the voucher to update; must not be {@code null}
   * @return {@code true} when the count was incremented; {@code false} when the voucher does not
   *     exist or its usage limit is already reached
   */
  boolean incrementUsageIfBelowLimit(Long id);
}
