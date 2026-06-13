/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: StockMovementRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:23:57
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.StockMovementEntity;
import com.nthn.storevision.repository.generic.SaveRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Domain repository for {@link StockMovementEntity}. Only supports writing (save) and targeted
 * reads. BR-I03: no delete operations are exposed — stock history is immutable.
 */
public interface StockMovementRepository extends SaveRepository<StockMovementEntity> {

  /**
   * Returns all movements for a product, ordered by the most recent first.
   *
   * @param productId the product to query
   * @return list of movements for that product
   */
  List<StockMovementEntity> findByProductId(Long productId);

  /**
   * Returns one page of RECEIPT movements within an optional date range, the most recent first.
   * Null bounds are treated as open-ended.
   *
   * @param from start of date range (inclusive), or {@code null} for no lower bound
   * @param to end of date range (inclusive), or {@code null} for no upper bound
   * @param pageable page request carrying index and size; must not be {@code null}
   * @return the requested page of matching RECEIPT movements; never {@code null}
   */
  Page<StockMovementEntity> findReceiptsBetween(
      LocalDateTime from, LocalDateTime to, Pageable pageable);
}
