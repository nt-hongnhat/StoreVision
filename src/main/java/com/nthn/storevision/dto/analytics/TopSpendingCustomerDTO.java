/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: TopSpendingCustomerDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.dto.analytics;

import com.nthn.storevision.constant.LoyaltyTier;
import java.math.BigDecimal;

/**
 * One row of the top-spending customers report, ranked by accumulated lifetime spending (BR-C04).
 *
 * @param customerId the customer's identifier
 * @param name the customer's full name
 * @param email the customer's email
 * @param loyaltyTier the customer's current loyalty tier
 * @param totalSpending accumulated lifetime spending; never {@code null}
 */
public record TopSpendingCustomerDTO(
    Long customerId, String name, String email, LoyaltyTier loyaltyTier, BigDecimal totalSpending) {}
