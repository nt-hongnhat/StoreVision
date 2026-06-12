/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: AnalyticsRetrievalService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:17:49
 */

package com.nthn.storevision.service.analytics;

import com.nthn.storevision.constant.AnalyticsPeriod;
import com.nthn.storevision.constant.RevenueGroupBy;
import com.nthn.storevision.dto.analytics.InventoryValuationDTO;
import com.nthn.storevision.dto.analytics.OrderSummaryDTO;
import com.nthn.storevision.dto.analytics.ProfitDTO;
import com.nthn.storevision.dto.analytics.RevenuePointDTO;
import com.nthn.storevision.dto.analytics.SlowMovingProductDTO;
import com.nthn.storevision.dto.analytics.TopSellingProductDTO;
import com.nthn.storevision.dto.analytics.TopSpendingCustomerDTO;
import java.time.LocalDate;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * Service interface for the read-only analytics and reporting module (3.5).
 *
 * <p>Date-ranged reports follow BR-A02: when {@code from}/{@code to} are omitted the current month
 * is used, and a range whose {@code from} is after {@code to} is rejected with an {@link
 * com.nthn.storevision.exception.InvalidDateRangeException}. Revenue figures only count COMPLETED
 * orders (BR-A01, enforced at the repository layer). Results are cached (BR-A03).
 */
public interface AnalyticsRetrievalService {

  /**
   * Values the whole current inventory: average RECEIPT cost times units in stock, per product plus
   * the grand total. Products never received are valued at zero.
   *
   * @return the inventory valuation; never {@code null}
   */
  InventoryValuationDTO getInventoryValuation();

  /**
   * Summarizes orders created in the range: totals per status and completed-order revenue.
   *
   * @param from inclusive range start; may be {@code null} — defaults to the 1st of this month
   * @param to inclusive range end; may be {@code null} — defaults to the last day of this month
   * @return the order summary; never {@code null}
   */
  OrderSummaryDTO getOrderSummary(@Nullable LocalDate from, @Nullable LocalDate to);

  /**
   * Computes gross profit over the range: completed-order revenue minus the cost of goods sold
   * (units sold times each product's average RECEIPT cost).
   *
   * @param from inclusive range start; may be {@code null} — defaults to the 1st of this month
   * @param to inclusive range end; may be {@code null} — defaults to the last day of this month
   * @return the profit report; never {@code null}
   */
  ProfitDTO getProfit(@Nullable LocalDate from, @Nullable LocalDate to);

  /**
   * Buckets completed-order revenue by day, week or month over the range.
   *
   * @param from inclusive range start; may be {@code null} — defaults to the 1st of this month
   * @param to inclusive range end; may be {@code null} — defaults to the last day of this month
   * @param groupBy the bucket size; must not be {@code null}
   * @return chronological revenue buckets; never {@code null}, empty buckets are omitted
   */
  List<RevenuePointDTO> getRevenue(
      @Nullable LocalDate from, @Nullable LocalDate to, @NonNull RevenueGroupBy groupBy);

  /**
   * Lists active products with the fewest completed sales in the look-back window, ties broken by
   * the highest remaining stock — products never sold rank first.
   *
   * @param limit maximum number of rows; must be positive
   * @param days length of the look-back window in days; must be positive
   * @return the slowest movers; never {@code null}
   */
  List<SlowMovingProductDTO> getSlowMovingProducts(int limit, int days);

  /**
   * Lists the best-selling products of the period by units sold.
   *
   * @param limit maximum number of rows; must be positive
   * @param period the rolling look-back window; must not be {@code null}
   * @return bestsellers, the highest quantity first; never {@code null}
   */
  List<TopSellingProductDTO> getTopSellingProducts(int limit, @NonNull AnalyticsPeriod period);

  /**
   * Lists the customers with the highest accumulated lifetime spending.
   *
   * @param limit maximum number of rows; must be positive
   * @return top spenders, the biggest first; never {@code null}
   */
  List<TopSpendingCustomerDTO> getTopSpendingCustomers(int limit);
}
