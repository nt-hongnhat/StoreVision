/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: RevenuePointView.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Spring Data interface projection for the native revenue-grouping query — one time bucket of
 * completed-order revenue (BR-A01). Column aliases in the native query must match these accessor
 * names.
 */
public interface RevenuePointView {

  /**
   * Gets the start of the time bucket produced by {@code date_trunc}.
   *
   * @return the bucket start; never {@code null}
   */
  LocalDateTime getPeriod();

  /**
   * Gets the total revenue of the bucket.
   *
   * @return the revenue; never {@code null}
   */
  BigDecimal getRevenue();

  /**
   * Gets the number of completed orders in the bucket.
   *
   * @return the order count; never {@code null}
   */
  Long getOrderCount();
}
