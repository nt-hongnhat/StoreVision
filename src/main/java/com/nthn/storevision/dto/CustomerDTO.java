/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 21:43:38
 */

package com.nthn.storevision.dto;

import com.nthn.storevision.constant.LoyaltyTier;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for Customer. Used to transfer customer data between layers, often for API
 * requests and responses.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
  private String address;

  /** Date of birth, used for birthday benefits; may be {@code null}. */
  private LocalDate dateOfBirth;

  @NotBlank(message = "{CSM008}")
  @Email(message = "{CSM009}")
  private String email;

  private Long id;

  /** Response-only: current loyalty point balance, managed by the loyalty flow (BR-C03). */
  private Long loyaltyPoints;

  /** Response-only: current loyalty tier, derived from total spending (BR-C04). */
  private LoyaltyTier loyaltyTier;

  @NotBlank(message = "{CSM007}")
  private String name;

  private String phone;

  /** Response-only: accumulated lifetime spending, managed by the loyalty flow (BR-C04). */
  private BigDecimal totalSpending;
}
