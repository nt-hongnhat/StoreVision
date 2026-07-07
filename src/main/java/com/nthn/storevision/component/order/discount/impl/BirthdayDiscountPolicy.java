/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: BirthdayDiscountPolicy.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-07-07 12:08:05
 */

package com.nthn.storevision.component.order.discount.impl;

import com.nthn.storevision.component.order.discount.DiscountPolicy;
import com.nthn.storevision.constant.loyalty_point.LoyaltyTier;
import com.nthn.storevision.dto.voucher.VoucherDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.util.DateUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

/**
 * {@link DiscountPolicy} that grants a birthday discount to {@link LoyaltyTier#SILVER} (5%) and
 * {@link LoyaltyTier#GOLD} (10%) customers when the order date falls on the customer's birthday.
 * The resulting discount is capped at the order subtotal.
 */
@Component
@Slf4j
public class BirthdayDiscountPolicy implements DiscountPolicy {

  private static final BigDecimal GOLD_BIRTHDAY_DISCOUNT_PERCENTAGE = BigDecimal.valueOf(10);
  private static final int MONEY_SCALE = 2;
  private static final BigDecimal PERCENT_DIVISOR = BigDecimal.valueOf(100);
  private static final BigDecimal SILVER_BIRTHDAY_DISCOUNT_PERCENTAGE = BigDecimal.valueOf(5);

  /** {@inheritDoc} */
  @Override
  public BigDecimal calculate(
      final @NonNull CustomerEntity customer,
      final @Nullable VoucherDTO voucher,
      final @NonNull BigDecimal subtotal) {
    log.debug("Evaluating birthday discount policy for the current order subtotal");
    if (null == customer.getDateOfBirth() || !DateUtil.isAnniversaryToday(customer.getDateOfBirth())) {
      log.debug(
          "Birthday discount policy not applied because the customer is not in birthday period");
      return BigDecimal.ZERO;
    }

    BigDecimal discountPercentage = BigDecimal.ZERO;
    if (LoyaltyTier.SILVER == customer.getLoyaltyTier()) {
      log.debug("Birthday discount policy matched SILVER tier branch");
      discountPercentage = SILVER_BIRTHDAY_DISCOUNT_PERCENTAGE;
    } else if (LoyaltyTier.GOLD == customer.getLoyaltyTier()) {
      log.debug("Birthday discount policy matched GOLD tier branch");
      discountPercentage = GOLD_BIRTHDAY_DISCOUNT_PERCENTAGE;
    } else {
      log.warn("Birthday discount policy found a tier without birthday discount configuration");
    }

    if (0 < discountPercentage.compareTo(BigDecimal.ZERO)) {
      final BigDecimal discount =
          subtotal
              .multiply(discountPercentage)
              .divide(PERCENT_DIVISOR, MONEY_SCALE, RoundingMode.HALF_UP)
              .min(subtotal);
      log.debug("Birthday discount policy applied successfully");
      return discount;
    }
    log.debug("Birthday discount policy completed with no discount");
    return BigDecimal.ZERO;
  }
}
