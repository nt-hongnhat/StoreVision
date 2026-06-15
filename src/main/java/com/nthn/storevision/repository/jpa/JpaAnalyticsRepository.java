/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: JpaAnalyticsRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 22:51:48
 */

package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.dto.analytics.OrderStatusCountDTO;
import com.nthn.storevision.dto.analytics.ProductCostDTO;
import com.nthn.storevision.dto.analytics.ProductSalesDTO;
import com.nthn.storevision.dto.analytics.ProductStockDTO;
import com.nthn.storevision.dto.analytics.TopSellingProductDTO;
import com.nthn.storevision.dto.analytics.TopSpendingCustomerDTO;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.repository.projection.RevenuePointView;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository hosting all read-only analytics projections (module 3.5). Anchored to {@link
 * OrderEntity} but queries span orders, order items, products, customers and stock movements.
 * BR-A01 is enforced directly in the queries: revenue figures only count COMPLETED orders.
 */
@Repository
public interface JpaAnalyticsRepository extends JpaRepository<OrderEntity, Long> {

  /**
   * Counts active orders per status within the range.
   *
   * @param from inclusive lower bound of the order date; must not be {@code null}
   * @param to exclusive upper bound of the order date; must not be {@code null}
   * @return one row per status present in the range; never {@code null}
   */
  @Query(
      "SELECT new com.nthn.storevision.dto.analytics.OrderStatusCountDTO(o.status, COUNT(o))"
          + " FROM OrderEntity o"
          + " WHERE o.deletedDate IS NULL AND o.orderDate >= :from AND o.orderDate < :to"
          + " GROUP BY o.status")
  List<OrderStatusCountDTO> countOrdersByStatus(
      @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

  /**
   * Computes the average RECEIPT unit cost per product across all recorded receipts.
   *
   * @return one row per product that has at least one costed receipt; never {@code null}
   */
  @Query(
      "SELECT new com.nthn.storevision.dto.analytics.ProductCostDTO(sm.product.id, AVG(sm.unitCost))"
          + " FROM StockMovementEntity sm"
          + " WHERE sm.type = com.nthn.storevision.constant.StockMovementType.RECEIPT"
          + " AND sm.unitCost IS NOT NULL"
          + " GROUP BY sm.product.id")
  List<ProductCostDTO> findAverageReceiptCosts();

  /**
   * Lists identity and current stock of all active products.
   *
   * @return one row per active product; never {@code null}
   */
  @Query(
          """
                  SELECT new com.nthn.storevision.dto.analytics.ProductStockDTO(p.id, p.name, p.stockQuantity)
                   FROM ProductEntity p WHERE p.deletedDate IS NULL""")
  List<ProductStockDTO> findActiveProductStocks();

  /**
   * BR-A01: sums units sold per product through active COMPLETED orders within the range.
   *
   * @param from inclusive lower bound of the order date; must not be {@code null}
   * @param to exclusive upper bound of the order date; must not be {@code null}
   * @return one row per product sold in the range; never {@code null}
   */
  @Query(
      """
                  SELECT new com.nthn.storevision.dto.analytics.ProductSalesDTO(oi.product.id, SUM(oi.quantity))
                   FROM OrderItemEntity oi
                   WHERE oi.order.status = com.nthn.storevision.constant.OrderStatus.COMPLETED
                   AND oi.order.deletedDate IS NULL
                   AND oi.order.orderDate >= :from AND oi.order.orderDate < :to
                   GROUP BY oi.product.id""")
  List<ProductSalesDTO> findProductSales(
      @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

  /**
   * BR-A01: revenue of active COMPLETED orders grouped into {@code date_trunc} buckets. Native
   * query — the grouping unit ({@code day}, {@code week}, {@code month}) is passed to PostgreSQL.
   *
   * @param unit the {@code date_trunc} unit; must not be {@code null}
   * @param from inclusive lower bound of the order date; must not be {@code null}
   * @param to exclusive upper bound of the order date; must not be {@code null}
   * @return one row per non-empty bucket, ordered chronologically; never {@code null}
   */
  @Query(
      value =
          "SELECT date_trunc(:unit, o.order_date) AS period,"
              + " SUM(o.total_amount) AS revenue,"
              + " COUNT(*) AS \"orderCount\""
              + " FROM orders o"
              + " WHERE o.status = 'COMPLETED' AND o.deleted_at IS NULL"
              + " AND o.order_date >= :from AND o.order_date < :to"
              + " GROUP BY period ORDER BY period",
      nativeQuery = true)
  List<RevenuePointView> findRevenueGroupedBy(
      @Param("unit") String unit, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

  /**
   * BR-A01: top products by units sold through active COMPLETED orders since {@code from}.
   *
   * @param from inclusive lower bound of the order date; must not be {@code null}
   * @param pageable page request carrying the row limit; must not be {@code null}
   * @return best-selling products, highest quantity first; never {@code null}
   */
  @Query(
      "SELECT new com.nthn.storevision.dto.analytics.TopSellingProductDTO("
          + "p.id, p.name, SUM(oi.quantity), SUM(oi.unitPrice * oi.quantity))"
          + " FROM OrderItemEntity oi JOIN oi.product p"
          + " WHERE oi.order.status = com.nthn.storevision.constant.OrderStatus.COMPLETED"
          + " AND oi.order.deletedDate IS NULL AND oi.order.orderDate >= :from"
          + " GROUP BY p.id, p.name"
          + " ORDER BY SUM(oi.quantity) DESC")
  List<TopSellingProductDTO> findTopSellingProducts(
      @Param("from") LocalDateTime from, Pageable pageable);

  /**
   * Ranks active customers by accumulated lifetime spending (BR-C04).
   *
   * @param pageable page request carrying the row limit; must not be {@code null}
   * @return top customers, biggest spender first; never {@code null}
   */
  @Query(
      "SELECT new com.nthn.storevision.dto.analytics.TopSpendingCustomerDTO("
          + "c.id, c.name, c.email, c.loyaltyTier, c.totalSpending)"
          + " FROM CustomerEntity c WHERE c.deletedDate IS NULL"
          + " ORDER BY c.totalSpending DESC")
  List<TopSpendingCustomerDTO> findTopSpendingCustomers(Pageable pageable);

  /**
   * BR-A01: total revenue of active COMPLETED orders within the range.
   *
   * @param from inclusive lower bound of the order date; must not be {@code null}
   * @param to exclusive upper bound of the order date; must not be {@code null}
   * @return the revenue sum, or {@code null} when no completed order falls in the range
   */
  @Query(
      "SELECT SUM(o.totalAmount) FROM OrderEntity o"
          + " WHERE o.status = com.nthn.storevision.constant.OrderStatus.COMPLETED"
          + " AND o.deletedDate IS NULL AND o.orderDate >= :from AND o.orderDate < :to")
  BigDecimal sumCompletedRevenue(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
