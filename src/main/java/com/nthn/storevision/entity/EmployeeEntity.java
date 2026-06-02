/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeEntity.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 21:38:39
 */

package com.nthn.storevision.entity;

import com.nthn.storevision.entity.mapped.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Builder
public class EmployeeEntity extends BaseEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String password; // Hashed password

  @Column(nullable = false)
  private String role; // e.g., ADMIN, MANAGER, STAFF

  @Column(nullable = false, unique = true)
  private String username;
}
