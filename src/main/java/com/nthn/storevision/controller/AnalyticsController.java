/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: AnalyticsController.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:52:00
 */

package com.nthn.storevision.controller;

import com.nthn.storevision.constant.analytics.AnalyticsPeriod;
import com.nthn.storevision.constant.analytics.RevenueGroupBy;
import com.nthn.storevision.dto.analytics.InventoryValuationDTO;
import com.nthn.storevision.dto.analytics.OrderSummaryDTO;
import com.nthn.storevision.dto.analytics.ProfitDTO;
import com.nthn.storevision.dto.analytics.RevenuePointDTO;
import com.nthn.storevision.dto.analytics.SlowMovingProductDTO;
import com.nthn.storevision.dto.analytics.TopSellingProductDTO;
import com.nthn.storevision.dto.analytics.TopSpendingCustomerDTO;
import java.time.LocalDate;
import java.util.List;

/**
 * API contract for the read-only analytics and reporting endpoints (module 3.5).
 *
 * @see com.nthn.storevision.controller.v1.AnalyticsControllerV1
 */
public interface AnalyticsController {

  /**
   * Values the whole current inventory: average RECEIPT cost times units in stock.
   *
   * @return the inventory valuation
   */
  InventoryValuationDTO getInventoryValuation();

  /**
   * Summarizes orders created in the range: totals per status and completed-order revenue.
   *
   * @param from inclusive range start; may be {@code null} — defaults to the current month
   * @param to inclusive range end; may be {@code null} — defaults to the current month
   * @return the order summary
   */
  OrderSummaryDTO getOrderSummary(LocalDate from, LocalDate to);

  /**
   * Computes gross profit (revenue − cost of goods sold) over the range.
   *
   * @param from inclusive range start; may be {@code null} — defaults to the current month
   * @param to inclusive range end; may be {@code null} — defaults to the current month
   * @return the profit report
   */
  ProfitDTO getProfit(LocalDate from, LocalDate to);

  /**
   * Buckets completed-order revenue by day, week or month over the range.
   *
   * @param from inclusive range start; may be {@code null} — defaults to the current month
   * @param to inclusive range end; may be {@code null} — defaults to the current month
   * @param groupBy the bucket size
   * @return chronological revenue buckets
   */
  List<RevenuePointDTO> getRevenue(LocalDate from, LocalDate to, RevenueGroupBy groupBy);

  /**
   * Lists active products with the fewest completed sales in the look-back window.
   *
   * @param limit maximum number of rows
   * @param days length of the look-back window in days
   * @return the slowest movers
   */
  List<SlowMovingProductDTO> getSlowMovingProducts(int limit, int days);

  /**
   * Lists the best-selling products of the period by units sold.
   *
   * @param limit maximum number of rows
   * @param period the rolling look-back window
   * @return best sellers, highest quantity first
   */
  List<TopSellingProductDTO> getTopSellingProducts(int limit, AnalyticsPeriod period);

  /**
   * Lists the customers with the highest accumulated lifetime spending.
   *
   * @param limit maximum number of rows
   * @return top spenders, biggest first
   */
  List<TopSpendingCustomerDTO> getTopSpendingCustomers(int limit);
}
