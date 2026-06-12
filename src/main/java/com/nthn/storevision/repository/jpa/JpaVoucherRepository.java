/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaVoucherRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.VoucherEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
