/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: LoyaltyPointHistoryRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.LoyaltyPointHistoryEntity;
import com.nthn.storevision.repository.generic.SaveRepository;
import java.util.List;

/**
 * Domain repository for {@link LoyaltyPointHistoryEntity}. Only supports write (save) and targeted
 * reads. BR-C05: no delete operations are exposed — point history is immutable.
 */
public interface LoyaltyPointHistoryRepository extends SaveRepository<LoyaltyPointHistoryEntity> {

  /**
   * Returns all point transactions of a customer, ordered by most recent first.
   *
   * @param customerId the customer to query; must not be {@code null}
   * @return list of point transactions for that customer; never {@code null}
   */
  List<LoyaltyPointHistoryEntity> findByCustomerId(Long customerId);
}
