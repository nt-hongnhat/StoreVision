/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: Voucher.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.model;

import com.nthn.storevision.constant.DiscountType;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a discount voucher in the StoreVision domain. A voucher is applied to an order at
 * creation time after passing the validation rules BR-V01 (not expired), BR-V02 (active), BR-V03
 * (usage limit not reached) and BR-V04 (minimum order amount met).
 */
public interface Voucher extends CustomAuditable<Long> {

  /**
   * Gets the unique code customers enter to apply this voucher.
   *
   * @return the voucher code as a {@link String}; never {@code null}.
   */
  String getCode();

  /**
   * Sets the unique code of this voucher.
   *
   * @param code the code to set; must not be {@code null}.
   * @return the updated {@code Voucher} instance.
   */
  Voucher setCode(String code);

  /**
   * Gets the type that defines how {@link #getDiscountValue()} is interpreted.
   *
   * @return the {@link DiscountType}; never {@code null}.
   */
  DiscountType getDiscountType();

  /**
   * Sets the discount type of this voucher.
   *
   * @param discountType the {@link DiscountType} to set; must not be {@code null}.
   * @return the updated {@code Voucher} instance.
   */
  Voucher setDiscountType(DiscountType discountType);

  /**
   * Gets the discount value: a percentage when the type is {@link DiscountType#PERCENTAGE}, an
   * amount in VND when the type is {@link DiscountType#FIXED_AMOUNT}.
   *
   * @return the discount value as a positive {@link BigDecimal}; never {@code null}.
   */
  BigDecimal getDiscountValue();

  /**
   * Sets the discount value of this voucher.
   *
   * @param discountValue the value to set; must not be {@code null} and must be positive.
   * @return the updated {@code Voucher} instance.
   */
  Voucher setDiscountValue(BigDecimal discountValue);

  /**
   * Gets the date this voucher stops being valid.
   *
   * @return the expiry date as a {@link LocalDate}; never {@code null}.
   */
  LocalDate getExpiryDate();

  /**
   * Sets the expiry date of this voucher.
   *
   * @param expiryDate the expiry date to set; must not be {@code null}.
   * @return the updated {@code Voucher} instance.
   */
  Voucher setExpiryDate(LocalDate expiryDate);

  /**
   * Gets the cap applied to a percentage discount.
   *
   * @return the maximum discount amount as a {@link BigDecimal}, or {@code null} when uncapped or
   *     when the type is {@link DiscountType#FIXED_AMOUNT}.
   */
  BigDecimal getMaxDiscountAmount();

  /**
   * Sets the cap applied to a percentage discount.
   *
   * @param maxDiscountAmount the cap to set; may be {@code null} for an uncapped discount.
   * @return the updated {@code Voucher} instance.
   */
  Voucher setMaxDiscountAmount(BigDecimal maxDiscountAmount);

  /**
   * Gets the total number of times this voucher may be used (BR-V03).
   *
   * @return the maximum usage as a positive {@link Integer}; never {@code null}.
   */
  Integer getMaxUsage();

  /**
   * Sets the total number of times this voucher may be used.
   *
   * @param maxUsage the maximum usage to set; must not be {@code null} and must be positive.
   * @return the updated {@code Voucher} instance.
   */
  Voucher setMaxUsage(Integer maxUsage);

  /**
   * Gets the minimum order amount required to apply this voucher (BR-V04).
   *
   * @return the minimum order amount as a {@link BigDecimal}, or {@code null} when no minimum
   *     applies.
   */
  BigDecimal getMinOrderAmount();

  /**
   * Sets the minimum order amount required to apply this voucher.
   *
   * @param minOrderAmount the minimum to set; may be {@code null} when no minimum applies.
   * @return the updated {@code Voucher} instance.
   */
  Voucher setMinOrderAmount(BigDecimal minOrderAmount);

  /**
   * Gets the date this voucher becomes valid.
   *
   * @return the start date as a {@link LocalDate}; never {@code null}.
   */
  LocalDate getStartDate();

  /**
   * Sets the date this voucher becomes valid.
   *
   * @param startDate the start date to set; must not be {@code null}.
   * @return the updated {@code Voucher} instance.
   */
  Voucher setStartDate(LocalDate startDate);

  /**
   * Gets the number of times this voucher has already been used. Incremented when an order using
   * the voucher is completed.
   *
   * @return the usage count as a non-negative {@link Integer}; never {@code null}.
   */
  Integer getUsageCount();

  /**
   * Sets the number of times this voucher has been used.
   *
   * @param usageCount the usage count to set; must not be {@code null} and must be non-negative.
   * @return the updated {@code Voucher} instance.
   */
  Voucher setUsageCount(Integer usageCount);

  /**
   * Indicates whether this voucher is currently activated (BR-V02).
   *
   * @return {@code true} if the voucher is active; {@code false} otherwise.
   */
  boolean isActive();

  /**
   * Sets the activation flag of this voucher.
   *
   * @param active {@code true} to activate the voucher; {@code false} to deactivate it.
   * @return the updated {@code Voucher} instance.
   */
  Voucher setActive(boolean active);
}
