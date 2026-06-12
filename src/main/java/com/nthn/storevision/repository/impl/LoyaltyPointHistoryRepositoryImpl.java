/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: LoyaltyPointHistoryRepositoryImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.repository.impl;

import com.nthn.storevision.entity.LoyaltyPointHistoryEntity;
import com.nthn.storevision.repository.LoyaltyPointHistoryRepository;
import com.nthn.storevision.repository.jpa.JpaLoyaltyPointHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the {@link LoyaltyPointHistoryRepository} interface. This class acts as an
 * adapter, delegating calls to the underlying {@link JpaLoyaltyPointHistoryRepository}.
 */
@Repository
@RequiredArgsConstructor
public class LoyaltyPointHistoryRepositoryImpl implements LoyaltyPointHistoryRepository {

  private final JpaLoyaltyPointHistoryRepository jpaRepository;

  @Override
  public List<LoyaltyPointHistoryEntity> findByCustomerId(final Long customerId) {
    return jpaRepository.findByCustomerIdOrderByTransactionDateDesc(customerId);
  }

  @Override
  public LoyaltyPointHistoryEntity save(final LoyaltyPointHistoryEntity history) {
    return jpaRepository.save(history);
  }
}
