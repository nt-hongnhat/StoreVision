/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaLoyaltyPointHistoryRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.LoyaltyPointHistoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for {@link LoyaltyPointHistoryEntity}. Provides standard and history-specific
 * queries.
 */
@Repository
public interface JpaLoyaltyPointHistoryRepository
    extends JpaRepository<LoyaltyPointHistoryEntity, Long> {

  /**
   * Returns all point transactions of a customer, ordered by most recent first.
   *
   * @param customerId the customer to query; must not be {@code null}
   * @return list of matching transactions; never {@code null}
   */
  List<LoyaltyPointHistoryEntity> findByCustomerIdOrderByTransactionDateDesc(Long customerId);
}
