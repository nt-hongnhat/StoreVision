/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: LoyaltyServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:01:00
 */

package com.nthn.storevision.service.customer.impl;

import com.nthn.storevision.constant.LoyaltyPointType;
import com.nthn.storevision.constant.LoyaltyTier;
import com.nthn.storevision.constant.message.CustomerMessageCode;
import com.nthn.storevision.dto.LoyaltyDetailsDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.entity.LoyaltyPointHistoryEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.LoyaltyPointHistoryMapper;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.repository.LoyaltyPointHistoryRepository;
import com.nthn.storevision.service.customer.LoyaltyService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link LoyaltyService}. Computes points from the customer's current
 * {@code EARN} entry to the point history (BR-C05).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoyaltyServiceImpl implements LoyaltyService {

  private static final int REDEEM_VOUCHER_CODE_LENGTH = 8;
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

  /** {@inheritDoc} */
  @Override
  @Transactional(readOnly = true)
  public LoyaltyDetailsDTO getLoyaltyDetails(final @NonNull Long customerId) {
    log.debug("Fetching loyalty details for customer id: {}", customerId);
    final CustomerEntity customer = findCustomerOrThrow(customerId);

    return LoyaltyDetailsDTO.builder()
        .customerId(customer.getId())
        .loyaltyTier(customer.getLoyaltyTier())
        .loyaltyPoints(customer.getLoyaltyPoints())
        .totalSpending(customer.getTotalSpending())
        .histories(
            loyaltyPointHistoryRepository.findByCustomerId(customerId).stream()
                .map(loyaltyPointHistoryMapper::toDTO)
                .toList())
        .build();
  }

  /** {@inheritDoc} */
  @Override
  @Transactional
  public VoucherDTO redeemPoints(final @NonNull Long customerId, final @NonNull Long points) {
    log.debug("Redeeming points: customerId={}, points={}", customerId, points);
    final CustomerEntity customer = findCustomerOrThrow(customerId);

    if (customer.getLoyaltyPoints() < points) {
      log.warn(
          "BR-C03 violation: customer id {} has {} points, requested {}",
          customerId,
          customer.getLoyaltyPoints(),
          points);
      throw new InsufficientLoyaltyPointsException(customer.getLoyaltyPoints(), points);
    }

    final VoucherDTO voucher = voucherCreationService.create(buildRedeemVoucher(points));

    final long newBalance = customer.getLoyaltyPoints() - points;
    customer.setLoyaltyPoints(newBalance);
    customerRepository.save(customer);

    loyaltyPointHistoryRepository.save(
        LoyaltyPointHistoryEntity.builder()
            .customer(customer)
            .type(LoyaltyPointType.REDEEM)
            .points(points)
            .balanceAfter(newBalance)
            .referenceVoucherId(voucher.getId())
            .transactionDate(LocalDateTime.now())
            .build());

    log.info(
        "Points redeemed: customerId={}, points={}, balance={}, voucherCode={}",
        customerId,
        points,
        newBalance,
        voucher.getCode());
    return voucher;
  }
  /**
   * Builds a single-use FIXED_AMOUNT voucher worth {@code points × pointValue} VND, valid from
   * today for the configured number of days.
   */
  private VoucherDTO buildRedeemVoucher(final Long points) {
    final LocalDate today = LocalDate.now();
    return VoucherDTO.builder()
        .code(generateRedeemVoucherCode())
        .discountType(DiscountType.FIXED_AMOUNT)
        .discountValue(pointValue.multiply(BigDecimal.valueOf(points)))
        .startDate(today)
        .expiryDate(today.plusDays(redeemVoucherValidityDays))
        .maxUsage(REDEEM_VOUCHER_MAX_USAGE)
        .usageCount(0)
        .active(true)
        .build();
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
