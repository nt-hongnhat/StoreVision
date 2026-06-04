/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderEntity.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 15:14:44
 */

package com.nthn.storevision.entity;

import com.nthn.storevision.constant.OrderStatus;
import com.nthn.storevision.entity.mapped.BaseEntity;
import com.nthn.storevision.model.Customer;
import com.nthn.storevision.model.Order;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Represents an order entity in the database. This class maps to the "orders" table and extends
 * {@link BaseEntity} for auditing purposes.
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Accessors(chain = true)
public class OrderEntity extends BaseEntity<Long> implements Order {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private CustomerEntity customer;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private LocalDateTime orderDate;

  @Builder.Default
  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
  private List<OrderItemEntity> orderItems = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private OrderStatus status;

  @Column(nullable = false)
  private BigDecimal totalAmount;

  @Override
  public Order setCustomer(final Customer customer) {
    this.customer = (CustomerEntity) customer;
    return this;
  }
}
