/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: StockMovementEntity.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 17:21:35
 */

package com.nthn.storevision.entity;

import com.nthn.storevision.constant.StockMovementType;
import com.nthn.storevision.model.Product;
import com.nthn.storevision.model.StockMovement;
import com.nthn.storevision.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Immutable record of a stock change. Has no soft-delete fields by design (BR-I03): once recorded,
 * a movement cannot be deleted.
 */
@Entity
@Table(name = "stock_movements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class StockMovementEntity implements StockMovement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "movement_date", nullable = false)
  private LocalDateTime movementDate;

  @Column(columnDefinition = "TEXT")
  private String note;

  /** BR-I01: the employee who performed this stock change — mandatory audit trail. */
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "performed_by", nullable = false)
  private UserEntity performedBy;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "product_id", nullable = false)
  private ProductEntity product;

  /** Always positive — direction is encoded in {@link #type}. */
  @Column(nullable = false)
  private Integer quantity;

  /** FK to the order that triggered this movement; populated only for an ISSUE type. */
  @Column(name = "reference_order_id")
  private Long referenceOrderId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private StockMovementType type;

  /** Unit cost at the time of receipt; null for ISSUE and ADJUSTMENT movements. */
  @Column(precision = 15, scale = 2)
  private BigDecimal unitCost;

  @Override
  public StockMovementEntity setPerformedBy(final User performedBy) {
    this.performedBy = (UserEntity) performedBy;
    return this;
  }

  @Override
  public StockMovementEntity setProduct(final Product product) {
    this.product = (ProductEntity) product;
    return this;
  }
}
