/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: VoucherDiscountPolicy.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-07-07 11:45:28
 */

package com.nthn.storevision.component.order.discount.impl;

import com.nthn.storevision.component.order.discount.DiscountPolicy;
import com.nthn.storevision.constant.voucher.DiscountType;
import com.nthn.storevision.dto.voucher.VoucherDTO;
import com.nthn.storevision.entity.CustomerEntity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

/**
 * {@link DiscountPolicy} that computes the discount from an applied voucher: either a percentage of
 * the subtotal (capped by the voucher's maximum discount amount when configured) or a fixed amount.
 * The resulting discount is capped at the order subtotal.
 */
@Component
@Slf4j
public class VoucherDiscountPolicy implements DiscountPolicy {

  private static final int MONEY_SCALE = 2;
  private static final BigDecimal PERCENT_DIVISOR = BigDecimal.valueOf(100);

  /** {@inheritDoc} */
  @Override
  public BigDecimal calculate(
      final @NonNull CustomerEntity customer,
      final @Nullable VoucherDTO voucher,
      final @NonNull BigDecimal subtotal) {
    log.debug("Evaluating voucher discount policy for the current order subtotal");
    if (null == voucher) {
      log.debug("Voucher discount policy completed with no discount because voucher is absent");
      return BigDecimal.ZERO;
    }
    BigDecimal discount;
    if (DiscountType.PERCENTAGE == voucher.getDiscountType()) {
      log.debug("Voucher discount policy selected percentage-based discount branch");
      discount =
          subtotal
              .multiply(voucher.getDiscountValue())
              .divide(PERCENT_DIVISOR, MONEY_SCALE, RoundingMode.HALF_UP);
      if (null != voucher.getMaxDiscountAmount()) {
        log.debug("Voucher discount policy applied configured maximum discount cap");
        discount = discount.min(voucher.getMaxDiscountAmount());
      }
    } else {
      log.debug("Voucher discount policy selected fixed-amount discount branch");
      discount = voucher.getDiscountValue();
    }
    final BigDecimal appliedDiscount = discount.min(subtotal);
    log.debug("Voucher discount policy completed and returned the computed discount");
    return appliedDiscount;
  }
}
