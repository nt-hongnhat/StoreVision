/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemEntity.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 15:12:30
 */

package com.nthn.storevision.entity;

import com.nthn.storevision.entity.mapped.BaseEntity;
import com.nthn.storevision.model.Order;
import com.nthn.storevision.model.OrderItem;
import com.nthn.storevision.model.Product;
import jakarta.persistence.*;
import java.math.BigDecimal;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Represents an order item entity in the database. This class maps to the "order_items" table and
 * extends {@link BaseEntity} for auditing purposes.
 */
@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Builder
@Accessors(chain = true)
public class OrderItemEntity extends BaseEntity<Long> implements OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private OrderEntity order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private ProductEntity product;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false)
  private BigDecimal unitPrice;

  @Override
  public OrderItem setOrder(final Order order) {
    this.order = (OrderEntity) order;
    return this;
  }

  @Override
  public OrderItem setProduct(final Product product) {
    this.product = (ProductEntity) product;
    return this;
  }
}
