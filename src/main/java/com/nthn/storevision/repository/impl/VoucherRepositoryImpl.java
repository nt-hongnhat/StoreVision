/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherRepositoryImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:08:52
 */

package com.nthn.storevision.repository.impl;

import com.nthn.storevision.entity.VoucherEntity;
import com.nthn.storevision.repository.VoucherRepository;
import com.nthn.storevision.repository.jpa.JpaVoucherRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Default implementation of {@link VoucherRepository} that delegates to {@link
 * JpaVoucherRepository}.
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
  public Page<VoucherEntity> findAllActive(final Pageable pageable) {
    return jpaRepository.findAllByDeletedDateIsNull(pageable);
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
    return jpaRepository.findByCodeAndDeletedDateIsNull(code);
  }

  @Override
  public boolean incrementUsageIfBelowLimit(final Long id) {
    return 0 < jpaRepository.incrementUsageIfBelowLimit(id);
  }

  @Override
  public VoucherEntity save(final VoucherEntity entity) {
    return jpaRepository.save(entity);
  }
}
