/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: VoucherEntity.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:52:00
 */

package com.nthn.storevision.entity;

import com.nthn.storevision.constant.voucher.DiscountType;
import com.nthn.storevision.entity.mapped.BaseEntity;
import com.nthn.storevision.model.Voucher;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Represents a voucher entity in the database. This class maps to the "vouchers" table and extends
 * {@link BaseEntity} for auditing purposes.
 */
@Entity
@Table(name = "vouchers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Accessors(chain = true)
public class VoucherEntity extends BaseEntity<Long> implements Voucher {

  @Column(name = "is_active", nullable = false)
  private boolean active;

  @Column(nullable = false, length = 50, unique = true)
  private String code;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private DiscountType discountType;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal discountValue;

  @Column(nullable = false)
  private LocalDate expiryDate;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(precision = 15, scale = 2)
  private BigDecimal maxDiscountAmount;

  @Column(nullable = false)
  private Integer maxUsage;

  @Column(precision = 15, scale = 2)
  private BigDecimal minOrderAmount;

  @Column(nullable = false)
  private LocalDate startDate;

  @Column(nullable = false)
  private Integer usageCount;
}
