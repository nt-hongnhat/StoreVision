/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherRepositoryImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.repository.impl;

import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.repository.VoucherRepository;
import com.nthn.storevision.repository.jpa.JpaVoucherRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the {@link VoucherRepository} interface.
 * This class acts as an adapter, delegating calls to the underlying {@link JpaVoucherRepository}.
 */
@Repository
@RequiredArgsConstructor
public class VoucherRepositoryImpl implements VoucherRepository {

  private final JpaVoucherRepository jpaRepository;

  @Override
  public List<VoucherEntity> findAll() {
    return jpaRepository.findAll();
  }

  @Override
  public Optional<VoucherEntity> findByCode(final String code) {
    return jpaRepository.findByCode(code);
  }

  @Override
  public Optional<VoucherEntity> findById(final Long id) {
    return jpaRepository.findById(id);
  }

  @Override
  public boolean isExistsById(final Long id) {
    return jpaRepository.existsById(id);
  }

  @Override
  public Optional<VoucherEntity> findByCode(final String code) {
    return jpaRepository.findByCode(code);
  }

  @Override
  public boolean incrementUsageIfBelowLimit(final Long id) {
    return 0 < jpaRepository.incrementUsageIfBelowLimit(id);
  }

  @Override
  public VoucherEntity save(final VoucherEntity voucher) {
    return jpaRepository.save(voucher);
  }
}
