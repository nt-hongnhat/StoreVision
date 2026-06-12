/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: LoyaltyServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 21:44:14
 */

package com.nthn.storevision.service.customer.impl;

import com.nthn.storevision.constant.LoyaltyPointType;
import com.nthn.storevision.constant.LoyaltyTier;
import com.nthn.storevision.constant.message.CustomerMessageCode;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.entity.LoyaltyPointHistoryEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.repository.LoyaltyPointHistoryRepository;
import com.nthn.storevision.service.customer.LoyaltyService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link LoyaltyService}. Computes points from the customer's current
 * tier rate, upgrades the tier based on accumulated spending (BR-C04) and appends an immutable
 * {@code EARN} entry to the point history (BR-C05).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoyaltyServiceImpl implements LoyaltyService {

  private final CustomerRepository customerRepository;
  private final LoyaltyPointHistoryRepository loyaltyPointHistoryRepository;

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void addPoints(
      final @NonNull Long customerId,
      final @NonNull BigDecimal orderAmount,
      final @NonNull Long referenceOrderId) {
    log.debug(
        "Adding loyalty points: customerId={}, orderAmount={}, orderId={}",
        customerId,
        orderAmount,
        referenceOrderId);

    final CustomerEntity customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(
                () -> new EntityNotFoundException(CustomerMessageCode.NOT_FOUND, customerId));

    final long pointsEarned = customer.getLoyaltyTier().calculatePoints(orderAmount);
    final long newBalance = customer.getLoyaltyPoints() + pointsEarned;

    customer.setLoyaltyPoints(newBalance);
    customer.setTotalSpending(customer.getTotalSpending().add(orderAmount));
    upgradeIfEligible(customer);
    customerRepository.save(customer);

    if (0 < pointsEarned) {
      loyaltyPointHistoryRepository.save(
          LoyaltyPointHistoryEntity.builder()
              .customer(customer)
              .type(LoyaltyPointType.EARN)
              .points(pointsEarned)
              .balanceAfter(newBalance)
              .referenceOrderId(referenceOrderId)
              .transactionDate(LocalDateTime.now())
              .build());
    }

    log.info(
        "Loyalty points added: customerId={}, earned={}, balance={}, tier={}",
        customerId,
        pointsEarned,
        newBalance,
        customer.getLoyaltyTier());
  }

  /** BR-C04: tiers are derived from lifetime spending and only ever move upwards. */
  private static void upgradeIfEligible(final @NonNull CustomerEntity customer) {
    final LoyaltyTier eligible = LoyaltyTier.fromTotalSpending(customer.getTotalSpending());
    if (eligible.ordinal() > customer.getLoyaltyTier().ordinal()) {
      log.info(
          "Customer id {} upgraded from {} to {}",
          customer.getId(),
          customer.getLoyaltyTier(),
          eligible);
      customer.setLoyaltyTier(eligible);
    }
  }
}
