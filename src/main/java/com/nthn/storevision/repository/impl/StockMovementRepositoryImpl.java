/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: StockMovementRepositoryImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:24:30
 */

package com.nthn.storevision.repository.impl;

import com.nthn.storevision.entity.StockMovementEntity;
import com.nthn.storevision.constant.StockMovementType;
import com.nthn.storevision.repository.StockMovementRepository;
import com.nthn.storevision.repository.jpa.JpaStockMovementRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/** Default implementation of {@link StockMovementRepository}. */
@Repository
@RequiredArgsConstructor
public class StockMovementRepositoryImpl implements StockMovementRepository {

  private final JpaStockMovementRepository jpaRepository;

  /** {@inheritDoc} */
  @Override
  public List<StockMovementEntity> findByProductId(final Long productId) {
    return jpaRepository.findByProductIdOrderByMovementDateDesc(productId);
  }

  /** {@inheritDoc} */
  @Override
  public List<StockMovementEntity> findReceiptsBetween(
      final LocalDateTime from, final LocalDateTime to) {
    return jpaRepository.findByTypeAndOptionalDateRange(StockMovementType.RECEIPT, from, to);
  }

  /** {@inheritDoc} */
  @Override
  public Page<StockMovementEntity> findReceiptsBetween(
      final LocalDateTime from, final LocalDateTime to, final Pageable pageable) {
    return jpaRepository.findPageByTypeAndOptionalDateRange(
        StockMovementType.RECEIPT, from, to, pageable);
  }

  /** {@inheritDoc} */
  @Override
  public StockMovementEntity save(final StockMovementEntity entity) {
    return jpaRepository.save(entity);
  }
}
