/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: LoyaltyPointHistoryEntity.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:52:00
 */

package com.nthn.storevision.entity;

import com.nthn.storevision.constant.loyalty_point.LoyaltyPointType;
import com.nthn.storevision.model.Customer;
import com.nthn.storevision.model.LoyaltyPointHistory;
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
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Immutable record of a loyalty point transaction. Has no soft-delete fields by design (BR-C05):
 * once recorded, a history entry cannot be deleted.
 */
@Entity
@Table(name = "loyalty_point_histories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class LoyaltyPointHistoryEntity implements LoyaltyPointHistory {

  /** BR-C03: the resulting balance is never negative. */
  @Column(name = "balance_after", nullable = false)
  private Long balanceAfter;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "customer_id", nullable = false)
  private CustomerEntity customer;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Always positive — direction is encoded in {@link #type}. */
  @Column(nullable = false)
  private Long points;

  /** FK to the order that earned the points; populated only for an EARN type. */
  @Column(name = "reference_order_id")
  private Long referenceOrderId;

  /** FK to the voucher obtained by redemption; populated only for a REDEEM type. */
  @Column(name = "reference_voucher_id")
  private Long referenceVoucherId;

  @Column(name = "transaction_date", nullable = false)
  private LocalDateTime transactionDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private LoyaltyPointType type;

  @Override
  public LoyaltyPointHistoryEntity setCustomer(final Customer customer) {
    this.customer = (CustomerEntity) customer;
    return this;
  }
}
