/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerEntity.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 21:42:56
 */

package com.nthn.storevision.entity;

import com.nthn.storevision.constant.LoyaltyTier;
import com.nthn.storevision.entity.mapped.BaseEntity;
import com.nthn.storevision.model.Customer;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Represents a customer entity in the database. This class maps to the "customers" table and
 * extends {@link BaseEntity} for auditing purposes.
 */
@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Accessors(chain = true)
public class CustomerEntity extends BaseEntity<Long> implements Customer {

  @Column private String address;

  @Column private LocalDate dateOfBirth;

  @Column(nullable = false, unique = true)
  private String email;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** BR-C03: the point balance is never negative. */
  @Builder.Default
  @Column(nullable = false)
  private Long loyaltyPoints = 0L;

  /** BR-C04: derived from {@link #totalSpending}; new customers start at BRONZE. */
  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private LoyaltyTier loyaltyTier = LoyaltyTier.BRONZE;

  @Column(nullable = false)
  private String name;

  @Column private String phone;

  /** BR-C04: accumulated lifetime spending; never resets. */
  @Builder.Default
  @Column(nullable = false, precision = 18, scale = 2)
  private BigDecimal totalSpending = BigDecimal.ZERO;
}
