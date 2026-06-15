/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: AnalyticsControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 22:47:43
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.analytics.AnalyticsPeriod;
import com.nthn.storevision.constant.analytics.RevenueGroupBy;
import com.nthn.storevision.constant.api.*;
import com.nthn.storevision.controller.AnalyticsController;
import com.nthn.storevision.dto.analytics.InventoryValuationDTO;
import com.nthn.storevision.dto.order.OrderSummaryDTO;
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
@RequestMapping(value = EndpointDefinitions.ANALYTICS, version = "v1")
@RequiredArgsConstructor
public class AnalyticsControllerV1 implements AnalyticsController {

  private final AnalyticsRetrievalService retrievalService;

  /** {@inheritDoc} */
  @Operation(
      summary = "Value the current inventory",
      description = OperationDescription.ANALYTICS_INVENTORY_VALUATION)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping("/inventory/valuation")
  @Override
  public InventoryValuationDTO getInventoryValuation() {
    log.debug("GET /analytics/inventory/valuation");
    return retrievalService.getInventoryValuation();
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Summarize orders over a range",
      description = OperationDescription.ANALYTICS_ORDER_SUMMARY)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.ANALYTICS_INVALID_RANGE)
  @GetMapping("/orders/summary")
  @Override
  public OrderSummaryDTO getOrderSummary(
      @Parameter(description = ParameterDescription.ANALYTICS_FROM_DATE)
          @RequestParam(required = false)
          final LocalDate from,
      @Parameter(description = ParameterDescription.ANALYTICS_TO_DATE)
          @RequestParam(required = false)
          final LocalDate to) {
    log.debug("GET /analytics/orders/summary — from: {}, to: {}", from, to);
    return retrievalService.getOrderSummary(from, to);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Compute gross profit over a range",
      description = OperationDescription.ANALYTICS_PROFIT)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.ANALYTICS_INVALID_RANGE)
  @GetMapping("/profit")
  @Override
  public ProfitDTO getProfit(
      @Parameter(description = ParameterDescription.ANALYTICS_FROM_DATE)
          @RequestParam(required = false)
          final LocalDate from,
      @Parameter(description = ParameterDescription.ANALYTICS_TO_DATE)
          @RequestParam(required = false)
          final LocalDate to) {
    log.debug("GET /analytics/profit — from: {}, to: {}", from, to);
    return retrievalService.getProfit(from, to);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Report revenue over a range",
      description = OperationDescription.ANALYTICS_REVENUE)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.ANALYTICS_INVALID_RANGE)
  @GetMapping("/revenue")
  @Override
  public List<RevenuePointDTO> getRevenue(
      @Parameter(description = ParameterDescription.ANALYTICS_FROM_DATE)
          @RequestParam(required = false)
          final LocalDate from,
      @Parameter(description = ParameterDescription.ANALYTICS_TO_DATE)
          @RequestParam(required = false)
          final LocalDate to,
      @Parameter(description = ParameterDescription.ANALYTICS_REVENUE_GROUP_BY)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_GROUP_BY)
          final RevenueGroupBy groupBy) {
    log.debug("GET /analytics/revenue — from: {}, to: {}, groupBy: {}", from, to, groupBy);
    return retrievalService.getRevenue(from, to, groupBy);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "List slow-moving products",
      description = OperationDescription.ANALYTICS_SLOW_MOVING_PRODUCTS)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping("/products/slow-moving")
  @Override
  public List<SlowMovingProductDTO> getSlowMovingProducts(
      @Parameter(description = ParameterDescription.ANALYTICS_LIMIT)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_LIMIT)
          final int limit,
      @Parameter(description = ParameterDescription.ANALYTICS_LOOKBACK_DAYS)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_DAYS)
          final int days) {
    log.debug("GET /analytics/products/slow-moving — limit: {}, days: {}", limit, days);
    return retrievalService.getSlowMovingProducts(limit, days);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "List top-selling products",
      description = OperationDescription.ANALYTICS_TOP_SELLING_PRODUCTS)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping("/products/top-selling")
  @Override
  public List<TopSellingProductDTO> getTopSellingProducts(
      @Parameter(description = ParameterDescription.ANALYTICS_LIMIT)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_LIMIT)
          final int limit,
      @Parameter(description = ParameterDescription.ANALYTICS_PERIOD)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_PERIOD)
          final AnalyticsPeriod period) {
    log.debug("GET /analytics/products/top-selling — limit: {}, period: {}", limit, period);
    return retrievalService.getTopSellingProducts(limit, period);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "List top-spending customers",
      description = OperationDescription.ANALYTICS_TOP_SPENDING_CUSTOMERS)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping("/customers/top-spending")
  @Override
  public List<TopSpendingCustomerDTO> getTopSpendingCustomers(
      @Parameter(description = ParameterDescription.ANALYTICS_LIMIT)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_LIMIT)
          final int limit) {
    log.debug("GET /analytics/customers/top-spending — limit: {}", limit);
    return retrievalService.getTopSpendingCustomers(limit);
  }
}
