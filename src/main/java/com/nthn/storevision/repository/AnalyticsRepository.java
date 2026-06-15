/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: AnalyticsRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 20:40:23
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.dto.analytics.OrderStatusCountDTO;
import com.nthn.storevision.dto.analytics.ProductCostDTO;
import com.nthn.storevision.dto.analytics.ProductSalesDTO;
import com.nthn.storevision.dto.analytics.ProductStockDTO;
import com.nthn.storevision.dto.analytics.TopSellingProductDTO;
import com.nthn.storevision.dto.analytics.TopSpendingCustomerDTO;
import com.nthn.storevision.repository.projection.RevenuePointView;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Domain repository for the read-only analytics module (3.5). Exposes aggregate projections only —
 * no entity mutation. BR-A01 is enforced inside every revenue-related query: only COMPLETED
 * orders are counted.
 */
public interface AnalyticsRepository {

  /**
   * Counts active orders per status within the range.
   *
   * @param from inclusive lower bound; must not be {@code null}
   * @param to exclusive upper bound; must not be {@code null}
   * @return one row per status present in the range; never {@code null}
   */
  List<OrderStatusCountDTO> countOrdersByStatus(LocalDateTime from, LocalDateTime to);

  /**
   * Computes the average RECEIPT unit cost per product.
   *
   * @return one row per product with at least one costed receipt; never {@code null}
   */
  List<ProductCostDTO> findAverageReceiptCosts();

  /**
   * Lists identity and current stock of all active products.
   *
   * @return one row per active product; never {@code null}
   */
  List<ProductStockDTO> findActiveProductStocks();

  /**
   * Sums units sold per product through COMPLETED orders within the range (BR-A01).
   *
   * @param from inclusive lower bound; must not be {@code null}
   * @param to exclusive upper bound; must not be {@code null}
   * @return one row per product sold in the range; never {@code null}
   */
  List<ProductSalesDTO> findProductSales(LocalDateTime from, LocalDateTime to);

  /**
   * Revenue of COMPLETED orders grouped into time buckets (BR-A01).
   *
   * @param truncUnit the PostgreSQL {@code date_trunc} unit; must not be {@code null}
   * @param from inclusive lower bound; must not be {@code null}
   * @param to exclusive upper bound; must not be {@code null}
   * @return chronological buckets; never {@code null}
   */
  List<RevenuePointView> findRevenueGroupedBy(
      String truncUnit, LocalDateTime from, LocalDateTime to);

  /**
   * Top products by units sold through COMPLETED orders since {@code from} (BR-A01).
   *
   * @param from inclusive lower bound; must not be {@code null}
   * @param limit maximum number of rows; must be positive
   * @return best sellers, highest quantity first; never {@code null}
   */
  List<TopSellingProductDTO> findTopSellingProducts(LocalDateTime from, int limit);

  /**
   * Ranks active customers by accumulated lifetime spending.
   *
   * @param limit maximum number of rows; must be positive
   * @return top customers, biggest spender first; never {@code null}
   */
  List<TopSpendingCustomerDTO> findTopSpendingCustomers(int limit);

  /**
   * Total revenue of COMPLETED orders within the range (BR-A01).
   *
   * @param from inclusive lower bound; must not be {@code null}
   * @param to exclusive upper bound; must not be {@code null}
   * @return the revenue sum; empty when no completed order falls in the range
   */
  Optional<BigDecimal> sumCompletedRevenue(LocalDateTime from, LocalDateTime to);
}
