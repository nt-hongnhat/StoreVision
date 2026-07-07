/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: DiscountPolicy.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-07-07 12:03:41
 */

package com.nthn.storevision.component.order.discount;

import com.nthn.storevision.dto.voucher.VoucherDTO;
import com.nthn.storevision.entity.CustomerEntity;
import java.math.BigDecimal;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/** Strategy contract for computing an order discount amount. */
@FunctionalInterface
public interface DiscountPolicy {

  /**
   * Computes the discount amount this policy contributes for the given order context.
   *
   * @param customer the customer placing the order; must not be {@code null}
   * @param voucher the validated voucher applied to the order; may be {@code null} if none supplied
   * @param subtotal the order subtotal to base the discount on; must not be {@code null}
   * @return the discount amount contributed by this policy; never negative and never {@code null},
   *     returning {@link BigDecimal#ZERO} when the policy does not apply
   */
  BigDecimal calculate(
      @NonNull CustomerEntity customer, @Nullable VoucherDTO voucher, @NonNull BigDecimal subtotal);
}
