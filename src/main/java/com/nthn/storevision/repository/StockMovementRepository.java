/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: StockMovementRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 00:00:00
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.StockMovementEntity;
import com.nthn.storevision.repository.generic.SaveRepository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Domain repository for {@link StockMovementEntity}. Only supports write (save) and targeted reads.
 * BR-I03: no delete operations are exposed — stock history is immutable.
 */
public interface StockMovementRepository extends SaveRepository<StockMovementEntity> {

  /**
   * Returns all movements for a product, ordered by most recent first.
   *
   * @param productId the product to query
   * @return list of movements for that product
   */
  List<StockMovementEntity> findByProductId(Long productId);

  /**
   * Returns RECEIPT movements within an optional date range. Null bounds are treated as open-ended.
   *
   * @param from start of date range (inclusive), or {@code null} for no lower bound
   * @param to end of date range (inclusive), or {@code null} for no upper bound
   * @return matching RECEIPT movements ordered by most recent first
   */
  List<StockMovementEntity> findReceiptsBetween(LocalDateTime from, LocalDateTime to);
}
