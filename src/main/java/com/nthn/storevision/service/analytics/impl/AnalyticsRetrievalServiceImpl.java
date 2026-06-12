/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: AnalyticsRetrievalServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.service.analytics.impl;

import com.nthn.storevision.constant.AnalyticsPeriod;
import com.nthn.storevision.constant.OrderStatus;
import com.nthn.storevision.constant.RevenueGroupBy;
import com.nthn.storevision.dto.analytics.InventoryValuationDTO;
import com.nthn.storevision.dto.analytics.InventoryValuationItemDTO;
import com.nthn.storevision.dto.analytics.OrderStatusCountDTO;
import com.nthn.storevision.dto.analytics.OrderSummaryDTO;
import com.nthn.storevision.dto.analytics.ProductCostDTO;
import com.nthn.storevision.dto.analytics.ProductSalesDTO;
import com.nthn.storevision.dto.analytics.ProfitDTO;
import com.nthn.storevision.dto.analytics.RevenuePointDTO;
import com.nthn.storevision.dto.analytics.SlowMovingProductDTO;
import com.nthn.storevision.dto.analytics.TopSellingProductDTO;
import com.nthn.storevision.dto.analytics.TopSpendingCustomerDTO;
import com.nthn.storevision.exception.InvalidDateRangeException;
import com.nthn.storevision.repository.AnalyticsRepository;
import com.nthn.storevision.service.analytics.AnalyticsRetrievalService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link AnalyticsRetrievalService}. Aggregation that JPQL cannot
 * express portably (cost merging, slow-mover ranking) is composed in memory from the repository's
 * read-only projections. All public methods are cached (BR-A03) and read-only transactional.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsRetrievalServiceImpl implements AnalyticsRetrievalService {

  private static final String CACHE_INVENTORY_VALUATION = "analytics_inventory_valuation";
  private static final String CACHE_ORDER_SUMMARY = "analytics_order_summary";
  private static final String CACHE_PROFIT = "analytics_profit";
  private static final String CACHE_REVENUE = "analytics_revenue";
  private static final String CACHE_SLOW_MOVING = "analytics_slow_moving";
  private static final String CACHE_TOP_SELLING = "analytics_top_selling";
  private static final String CACHE_TOP_SPENDING = "analytics_top_spending";

  private static final int MONEY_SCALE = 2;

  private final AnalyticsRepository analyticsRepository;

  /** {@inheritDoc} */
  @Override
  @Cacheable(cacheNames = CACHE_INVENTORY_VALUATION)
  @Transactional(readOnly = true)
  public InventoryValuationDTO getInventoryValuation() {
    log.debug("Computing inventory valuation");
    final Map<Long, BigDecimal> costs = averageCostsByProduct();

    final List<InventoryValuationItemDTO> items =
        analyticsRepository.findActiveProductStocks().stream()
            .map(
                stock -> {
                  final BigDecimal unitCost =
                      costs.getOrDefault(stock.productId(), BigDecimal.ZERO);
                  final BigDecimal value =
                      unitCost
                          .multiply(BigDecimal.valueOf(stock.stockQuantity()))
                          .setScale(MONEY_SCALE, RoundingMode.HALF_UP);
                  return new InventoryValuationItemDTO(
                      stock.productId(), stock.productName(), stock.stockQuantity(), unitCost, value);
                })
            .toList();

    final BigDecimal totalValue =
        items.stream()
            .map(InventoryValuationItemDTO::stockValue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    return new InventoryValuationDTO(totalValue, items);
  }

  /** {@inheritDoc} */
  @Override
  @Cacheable(cacheNames = CACHE_ORDER_SUMMARY)
  @Transactional(readOnly = true)
  public OrderSummaryDTO getOrderSummary(
      final @Nullable LocalDate from, final @Nullable LocalDate to) {
    final DateRange range = resolveRange(from, to);
    log.debug("Computing order summary: {} – {}", range.from(), range.to());

    final Map<OrderStatus, Long> counts =
        analyticsRepository.countOrdersByStatus(range.fromStart(), range.toExclusive()).stream()
            .collect(
                Collectors.toMap(OrderStatusCountDTO::status, OrderStatusCountDTO::count));
    final long pending = counts.getOrDefault(OrderStatus.PENDING, 0L);
    final long completed = counts.getOrDefault(OrderStatus.COMPLETED, 0L);
    final long cancelled = counts.getOrDefault(OrderStatus.CANCELLED, 0L);

    final BigDecimal revenue =
        analyticsRepository
            .sumCompletedRevenue(range.fromStart(), range.toExclusive())
            .orElse(BigDecimal.ZERO);

    return new OrderSummaryDTO(
        range.from(), range.to(), pending + completed + cancelled, pending, completed, cancelled,
        revenue);
  }

  /** {@inheritDoc} */
  @Override
  @Cacheable(cacheNames = CACHE_PROFIT)
  @Transactional(readOnly = true)
  public ProfitDTO getProfit(final @Nullable LocalDate from, final @Nullable LocalDate to) {
    final DateRange range = resolveRange(from, to);
    log.debug("Computing gross profit: {} – {}", range.from(), range.to());

    final BigDecimal revenue =
        analyticsRepository
            .sumCompletedRevenue(range.fromStart(), range.toExclusive())
            .orElse(BigDecimal.ZERO);

    final Map<Long, BigDecimal> costs = averageCostsByProduct();
    final BigDecimal costOfGoodsSold =
        analyticsRepository.findProductSales(range.fromStart(), range.toExclusive()).stream()
            .map(
                sales ->
                    costs
                        .getOrDefault(sales.productId(), BigDecimal.ZERO)
                        .multiply(BigDecimal.valueOf(sales.quantitySold())))
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .setScale(MONEY_SCALE, RoundingMode.HALF_UP);

    return new ProfitDTO(
        range.from(), range.to(), revenue, costOfGoodsSold, revenue.subtract(costOfGoodsSold));
  }

  /** {@inheritDoc} */
  @Override
  @Cacheable(cacheNames = CACHE_REVENUE)
  @Transactional(readOnly = true)
  public List<RevenuePointDTO> getRevenue(
      final @Nullable LocalDate from,
      final @Nullable LocalDate to,
      final @NonNull RevenueGroupBy groupBy) {
    final DateRange range = resolveRange(from, to);
    log.debug("Computing revenue: {} – {}, groupBy={}", range.from(), range.to(), groupBy);

    return analyticsRepository
        .findRevenueGroupedBy(groupBy.getTruncUnit(), range.fromStart(), range.toExclusive())
        .stream()
        .map(point -> new RevenuePointDTO(point.getPeriod(), point.getRevenue(), point.getOrderCount()))
        .toList();
  }

  /** {@inheritDoc} */
  @Override
  @Cacheable(cacheNames = CACHE_SLOW_MOVING)
  @Transactional(readOnly = true)
  public List<SlowMovingProductDTO> getSlowMovingProducts(final int limit, final int days) {
    log.debug("Computing slow-moving products: limit={}, days={}", limit, days);
    final LocalDateTime from = LocalDate.now().minusDays(days).atStartOfDay();
    final LocalDateTime to = LocalDate.now().plusDays(1).atStartOfDay();

    final Map<Long, Long> sold =
        analyticsRepository.findProductSales(from, to).stream()
            .collect(Collectors.toMap(ProductSalesDTO::productId, ProductSalesDTO::quantitySold));

    return analyticsRepository.findActiveProductStocks().stream()
        .map(
            stock ->
                new SlowMovingProductDTO(
                    stock.productId(),
                    stock.productName(),
                    stock.stockQuantity(),
                    sold.getOrDefault(stock.productId(), 0L)))
        .sorted(
            Comparator.comparingLong(SlowMovingProductDTO::quantitySold)
                .thenComparing(SlowMovingProductDTO::stockQuantity, Comparator.reverseOrder()))
        .limit(limit)
        .toList();
  }

  /** {@inheritDoc} */
  @Override
  @Cacheable(cacheNames = CACHE_TOP_SELLING)
  @Transactional(readOnly = true)
  public List<TopSellingProductDTO> getTopSellingProducts(
      final int limit, final @NonNull AnalyticsPeriod period) {
    log.debug("Computing top-selling products: limit={}, period={}", limit, period);
    return analyticsRepository.findTopSellingProducts(period.resolveStart(LocalDate.now()), limit);
  }

  /** {@inheritDoc} */
  @Override
  @Cacheable(cacheNames = CACHE_TOP_SPENDING)
  @Transactional(readOnly = true)
  public List<TopSpendingCustomerDTO> getTopSpendingCustomers(final int limit) {
    log.debug("Computing top-spending customers: limit={}", limit);
    return analyticsRepository.findTopSpendingCustomers(limit);
  }

  private Map<Long, BigDecimal> averageCostsByProduct() {
    return analyticsRepository.findAverageReceiptCosts().stream()
        .collect(
            Collectors.toMap(
                ProductCostDTO::productId,
                cost ->
                    BigDecimal.valueOf(cost.averageUnitCost())
                        .setScale(MONEY_SCALE, RoundingMode.HALF_UP),
                (first, second) -> first));
  }

  /**
   * BR-A02: defaults the range to the current month when bounds are omitted and rejects ranges
   * whose {@code from} is after {@code to}.
   */
  private static DateRange resolveRange(
      final @Nullable LocalDate from, final @Nullable LocalDate to) {
    final YearMonth currentMonth = YearMonth.now();
    final LocalDate resolvedFrom = null != from ? from : currentMonth.atDay(1);
    final LocalDate resolvedTo = null != to ? to : currentMonth.atEndOfMonth();
    if (resolvedFrom.isAfter(resolvedTo)) {
      throw new InvalidDateRangeException(resolvedFrom, resolvedTo);
    }
    return new DateRange(resolvedFrom, resolvedTo);
  }

  /** Inclusive reporting range with helpers for the half-open query bounds. */
  private record DateRange(LocalDate from, LocalDate to) {

    LocalDateTime fromStart() {
      return from.atStartOfDay();
    }

    LocalDateTime toExclusive() {
      return to.plusDays(1).atStartOfDay();
    }
  }
}
