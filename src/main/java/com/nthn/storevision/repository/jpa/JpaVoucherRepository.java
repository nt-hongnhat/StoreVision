/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaVoucherRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:05:03
 */

package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.VoucherEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** JPA Repository for {@link VoucherEntity}. Provides standard and voucher-specific queries. */
@Repository
public interface JpaVoucherRepository extends JpaRepository<VoucherEntity, Long> {

  /**
   * Finds a voucher by its unique code.
   *
   * @param code the voucher code to look up; must not be {@code null}
   * @return an {@link Optional} containing the matching {@link VoucherEntity}, or empty if none
   */
  Optional<VoucherEntity> findByCode(String code);

  /**
   * Returns all active (non-soft-deleted) vouchers.
   *
   * @return the list of active vouchers; never {@code null}
   */
  List<VoucherEntity> findByDeletedDateIsNull();

  /**
   * Returns one page of active (non-soft-deleted) vouchers.
   *
   * @param pageable page request carrying index, size and sort; must not be {@code null}
   * @return the requested page of active vouchers; never {@code null}
   */
  Page<VoucherEntity> findByDeletedDateIsNull(Pageable pageable);

  /**
   * BR-V03: atomically increments a voucher's usage count only while it is still below the usage
   * limit, in a single guarded UPDATE statement. Two concurrent order completions can therefore
   * never push the usage count past {@code maxUsage}.
   *
   * @param id the voucher to update; must not be {@code null}
   * @return the number of rows updated: 1 on success, 0 when the voucher does not exist or its
   *     usage limit is already reached
   */
  @Modifying
  @Query(
      "UPDATE VoucherEntity v SET v.usageCount = v.usageCount + 1"
          + " WHERE v.id = :id AND v.usageCount < v.maxUsage")
  int incrementUsageIfBelowLimit(@Param("id") Long id);
}
