/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerEntity.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 15:11:52
 */

package com.nthn.storevision.entity;

import com.nthn.storevision.entity.mapped.BaseEntity;
import com.nthn.storevision.model.Customer;
import jakarta.persistence.*;
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
@Builder
@Accessors(chain = true)
public class CustomerEntity extends BaseEntity<Long> implements Customer {

  @Column private String address;

  @Column(nullable = false, unique = true)
  private String email;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column private String phone;
}
