/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaVoucherRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13
 */

package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.VoucherEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link VoucherEntity}.
 */
@Repository
public interface JpaVoucherRepository extends JpaRepository<VoucherEntity, Long> {

  /**
   * Finds a voucher by its unique code, ignoring soft-deleted records.
   *
   * @param code the voucher code to look up; must not be {@code null}
   * @return an {@link Optional} containing the matching {@link VoucherEntity}, or empty if no
   *     voucher carries the given code or it is soft-deleted
   */
  Optional<VoucherEntity> findByCodeAndDeletedDateIsNull(String code);

  /**
   * Retrieves a paginated list of all active (non-deleted) vouchers.
   *
   * @param pageable the pagination information including page number and size; must not be null
   * @return a {@code Page} containing the active vouchers
   */
  Page<VoucherEntity> findAllByDeletedDateIsNull(Pageable pageable);

  /**
   * Atomically increments a voucher's usage count only while it is still below the usage limit.
   *
   * @param id the voucher to update; must not be {@code null}
   * @return the number of rows updated (0 or 1)
   */
  @Modifying
  @Query(
      "UPDATE VoucherEntity v SET v.usageCount = v.usageCount + 1 "
          + "WHERE v.id = :id AND v.usageCount < v.maxUsage AND v.deletedDate IS NULL")
  int incrementUsageIfBelowLimit(Long id);
}