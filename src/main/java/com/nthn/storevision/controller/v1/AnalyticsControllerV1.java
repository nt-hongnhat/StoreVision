/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: AnalyticsControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.AnalyticsPeriod;
import com.nthn.storevision.constant.RevenueGroupBy;
import com.nthn.storevision.constant.api.ResponseCode;
import com.nthn.storevision.constant.api.ResponseDescription;
import com.nthn.storevision.controller.AnalyticsController;
import com.nthn.storevision.dto.analytics.InventoryValuationDTO;
import com.nthn.storevision.dto.analytics.OrderSummaryDTO;
import com.nthn.storevision.dto.analytics.ProfitDTO;
import com.nthn.storevision.dto.analytics.RevenuePointDTO;
import com.nthn.storevision.dto.analytics.SlowMovingProductDTO;
import com.nthn.storevision.dto.analytics.TopSellingProductDTO;
import com.nthn.storevision.dto.analytics.TopSpendingCustomerDTO;
import com.nthn.storevision.service.analytics.AnalyticsRetrievalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Version 1 implementation of {@link AnalyticsController}. */
@Tag(name = "Analytics", description = "Revenue, profit and statistics reporting endpoints")
@Slf4j
@RestController
@RequestMapping(value = "/analytics", version = "v1")
@RequiredArgsConstructor
public class AnalyticsControllerV1 implements AnalyticsController {

  private static final String DEFAULT_DAYS = "30";
  private static final String DEFAULT_GROUP_BY = "day";
  private static final String DEFAULT_LIMIT = "10";
  private static final String DEFAULT_PERIOD = "month";

  private static final String FROM_DESC =
      "Inclusive range start (ISO date); defaults to the 1st of the current month (BR-A02)";
  private static final String TO_DESC =
      "Inclusive range end (ISO date); defaults to the last day of the current month (BR-A02)";
  private static final String INVALID_RANGE_DESC =
      "The 'from' date is after the 'to' date (BR-A02)";
  private static final String LIMIT_DESC = "Maximum number of rows to return";
  private static final String REVENUE_DESC =
      "Buckets completed-order revenue (BR-A01) by day, week or month over the requested range.";
  private static final String PROFIT_DESC =
      "Gross profit over the range: completed-order revenue (BR-A01) minus cost of goods sold,"
          + " where unit cost is each product's average RECEIPT cost.";
  private static final String TOP_SELLING_DESC =
      "Best-selling products by units sold through completed orders (BR-A01) within the rolling"
          + " period (today, last 7 days or last 30 days).";
  private static final String SLOW_MOVING_DESC =
      "Active products with the fewest completed sales in the look-back window; products never"
          + " sold rank first, ties broken by the highest remaining stock.";
  private static final String TOP_SPENDING_DESC =
      "Customers ranked by accumulated lifetime spending (BR-C04).";
  private static final String ORDER_SUMMARY_DESC =
      "Order totals per status plus completed-order revenue (BR-A01) for the requested range.";
  private static final String VALUATION_DESC =
      "Current inventory value: average RECEIPT unit cost times units in stock, per product plus"
          + " the grand total. Products never received are valued at zero.";

  private final AnalyticsRetrievalService retrievalService;

  /** {@inheritDoc} */
  @Operation(summary = "Value the current inventory", description = VALUATION_DESC)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping("/inventory/valuation")
  @Override
  public InventoryValuationDTO getInventoryValuation() {
    log.debug("GET /analytics/inventory/valuation");
    return retrievalService.getInventoryValuation();
  }

  /** {@inheritDoc} */
  @Operation(summary = "Summarize orders over a range", description = ORDER_SUMMARY_DESC)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.BAD_REQUEST, description = INVALID_RANGE_DESC)
  @GetMapping("/orders/summary")
  @Override
  public OrderSummaryDTO getOrderSummary(
      @Parameter(description = FROM_DESC) @RequestParam(required = false) final LocalDate from,
      @Parameter(description = TO_DESC) @RequestParam(required = false) final LocalDate to) {
    log.debug("GET /analytics/orders/summary — from: {}, to: {}", from, to);
    return retrievalService.getOrderSummary(from, to);
  }

  /** {@inheritDoc} */
  @Operation(summary = "Compute gross profit over a range", description = PROFIT_DESC)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.BAD_REQUEST, description = INVALID_RANGE_DESC)
  @GetMapping("/profit")
  @Override
  public ProfitDTO getProfit(
      @Parameter(description = FROM_DESC) @RequestParam(required = false) final LocalDate from,
      @Parameter(description = TO_DESC) @RequestParam(required = false) final LocalDate to) {
    log.debug("GET /analytics/profit — from: {}, to: {}", from, to);
    return retrievalService.getProfit(from, to);
  }

  /** {@inheritDoc} */
  @Operation(summary = "Report revenue over a range", description = REVENUE_DESC)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.BAD_REQUEST, description = INVALID_RANGE_DESC)
  @GetMapping("/revenue")
  @Override
  public List<RevenuePointDTO> getRevenue(
      @Parameter(description = FROM_DESC) @RequestParam(required = false) final LocalDate from,
      @Parameter(description = TO_DESC) @RequestParam(required = false) final LocalDate to,
      @Parameter(description = "Bucket size: day, week or month")
          @RequestParam(defaultValue = DEFAULT_GROUP_BY)
          final RevenueGroupBy groupBy) {
    log.debug("GET /analytics/revenue — from: {}, to: {}, groupBy: {}", from, to, groupBy);
    return retrievalService.getRevenue(from, to, groupBy);
  }

  /** {@inheritDoc} */
  @Operation(summary = "List slow-moving products", description = SLOW_MOVING_DESC)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping("/products/slow-moving")
  @Override
  public List<SlowMovingProductDTO> getSlowMovingProducts(
      @Parameter(description = LIMIT_DESC) @RequestParam(defaultValue = DEFAULT_LIMIT)
          final int limit,
      @Parameter(description = "Length of the look-back window in days")
          @RequestParam(defaultValue = DEFAULT_DAYS)
          final int days) {
    log.debug("GET /analytics/products/slow-moving — limit: {}, days: {}", limit, days);
    return retrievalService.getSlowMovingProducts(limit, days);
  }

  /** {@inheritDoc} */
  @Operation(summary = "List top-selling products", description = TOP_SELLING_DESC)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping("/products/top-selling")
  @Override
  public List<TopSellingProductDTO> getTopSellingProducts(
      @Parameter(description = LIMIT_DESC) @RequestParam(defaultValue = DEFAULT_LIMIT)
          final int limit,
      @Parameter(description = "Rolling look-back window: today, week or month")
          @RequestParam(defaultValue = DEFAULT_PERIOD)
          final AnalyticsPeriod period) {
    log.debug("GET /analytics/products/top-selling — limit: {}, period: {}", limit, period);
    return retrievalService.getTopSellingProducts(limit, period);
  }

  /** {@inheritDoc} */
  @Operation(summary = "List top-spending customers", description = TOP_SPENDING_DESC)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping("/customers/top-spending")
  @Override
  public List<TopSpendingCustomerDTO> getTopSpendingCustomers(
      @Parameter(description = LIMIT_DESC) @RequestParam(defaultValue = DEFAULT_LIMIT)
          final int limit) {
    log.debug("GET /analytics/customers/top-spending — limit: {}", limit);
    return retrievalService.getTopSpendingCustomers(limit);
  }
}
