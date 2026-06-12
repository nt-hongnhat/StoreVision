/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaStockMovementRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:23:57
 */

package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.constant.StockMovementType;
import com.nthn.storevision.entity.StockMovementEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** JPA Repository for {@link StockMovementEntity}. */
@Repository
public interface JpaStockMovementRepository extends JpaRepository<StockMovementEntity, Long> {

  /**
   * Returns all movements for a given product, ordered by the most recent first.
   *
   * @param productId the product id to filter by; must not be {@code null}
   * @return movements for that product, newest first
   */
  List<StockMovementEntity> findByProductIdOrderByMovementDateDesc(Long productId);

  /**
   * Returns movements of the given type within an optional date range. {@code null} bounds are
   * treated as open-ended (no lower or upper limit applied).
   *
   * @param type the movement type to filter by; must not be {@code null}
   * @param from start of date range (inclusive), or {@code null}
   * @param to end of date range (inclusive), or {@code null}
   * @return matching movements ordered by the most recent first
   */
  @Query(
      """
          SELECT s FROM StockMovementEntity s
           WHERE s.type = :type
           AND (:from IS NULL OR s.movementDate >= :from)
           AND (:to   IS NULL OR s.movementDate <= :to)
           ORDER BY s.movementDate DESC""")
  List<StockMovementEntity> findByTypeAndOptionalDateRange(
      @Param("type") StockMovementType type,
      @Param("from") LocalDateTime from,
      @Param("to") LocalDateTime to);

  /**
   * Returns one page of movements of the given type within an optional date range, the most recent
   * first. {@code null} bounds are treated as open-ended.
   *
   * @param type the movement type to filter by; must not be {@code null}
   * @param from start of date range (inclusive), or {@code null}
   * @param to end of date range (inclusive), or {@code null}
   * @param pageable page request carrying index and size; must not be {@code null}
   * @return the requested page of matching movements; never {@code null}
   */
  @Query(
      """
          SELECT s FROM StockMovementEntity s
           WHERE s.type = :type
           AND (:from IS NULL OR s.movementDate >= :from)
           AND (:to   IS NULL OR s.movementDate <= :to)
           ORDER BY s.movementDate DESC""")
  Page<StockMovementEntity> findPageByTypeAndOptionalDateRange(
      @Param("type") StockMovementType type,
      @Param("from") LocalDateTime from,
      @Param("to") LocalDateTime to,
      Pageable pageable);
}
