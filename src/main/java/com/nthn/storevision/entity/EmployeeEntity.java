/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: EmployeeEntity.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:51:59
 */

package com.nthn.storevision.entity;

import com.nthn.storevision.constant.employee.EmployeeRole;
import com.nthn.storevision.entity.mapped.BaseEntity;
import com.nthn.storevision.model.Employee;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Represents an employee entity in the database. This class maps to the "employees" table and
 * extends {@link BaseEntity} for auditing purposes.
 */
@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Builder
@Accessors(chain = true)
public class EmployeeEntity extends BaseEntity<Long> implements Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String password; // Hashed password

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private EmployeeRole role;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private UserEntity user;

  @Column(name = "user_id")
  private Long userId;

  @Column(nullable = false, unique = true)
  private String username;
}
