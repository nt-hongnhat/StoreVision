/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: AnalyticsRepositoryImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 20:40:23
 */

package com.nthn.storevision.repository.impl;

import com.nthn.storevision.dto.analytics.OrderStatusCountDTO;
import com.nthn.storevision.dto.analytics.ProductCostDTO;
import com.nthn.storevision.dto.analytics.ProductSalesDTO;
import com.nthn.storevision.dto.analytics.ProductStockDTO;
import com.nthn.storevision.dto.analytics.TopSellingProductDTO;
import com.nthn.storevision.dto.analytics.TopSpendingCustomerDTO;
import com.nthn.storevision.repository.AnalyticsRepository;
import com.nthn.storevision.repository.jpa.JpaAnalyticsRepository;
import com.nthn.storevision.repository.projection.RevenuePointView;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the {@link AnalyticsRepository} interface. This class acts as an adapter,
 * delegating calls to the underlying {@link JpaAnalyticsRepository} and translating plain row
 * limits into {@link PageRequest}s.
 */
@Repository
@RequiredArgsConstructor
public class AnalyticsRepositoryImpl implements AnalyticsRepository {

  private final JpaAnalyticsRepository jpaRepository;

  @Override
  public List<OrderStatusCountDTO> countOrdersByStatus(
      final LocalDateTime from, final LocalDateTime to) {
    return jpaRepository.countOrdersByStatus(from, to);
  }

  @Override
  public List<ProductCostDTO> findAverageReceiptCosts() {
    return jpaRepository.findAverageReceiptCosts();
  }

  @Override
  public List<ProductStockDTO> findActiveProductStocks() {
    return jpaRepository.findActiveProductStocks();
  }

  @Override
  public List<ProductSalesDTO> findProductSales(
      final LocalDateTime from, final LocalDateTime to) {
    return jpaRepository.findProductSales(from, to);
  }

  @Override
  public List<RevenuePointView> findRevenueGroupedBy(
      final String truncUnit, final LocalDateTime from, final LocalDateTime to) {
    return jpaRepository.findRevenueGroupedBy(truncUnit, from, to);
  }

  @Override
  public List<TopSellingProductDTO> findTopSellingProducts(
      final LocalDateTime from, final int limit) {
    return jpaRepository.findTopSellingProducts(from, PageRequest.ofSize(limit));
  }

  @Override
  public List<TopSpendingCustomerDTO> findTopSpendingCustomers(final int limit) {
    return jpaRepository.findTopSpendingCustomers(PageRequest.ofSize(limit));
  }

  @Override
  public Optional<BigDecimal> sumCompletedRevenue(
      final LocalDateTime from, final LocalDateTime to) {
    return Optional.ofNullable(jpaRepository.sumCompletedRevenue(from, to));
  }
}
