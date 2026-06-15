/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: CustomerDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 22:47:43
 */

package com.nthn.storevision.dto.customer;

import com.nthn.storevision.constant.loyalty_point.LoyaltyTier;
import com.nthn.storevision.dto.response.HasIdentifier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.*;

/**
 * Data Transfer Object for Customer. Used to transfer customer data between layers, often for API
 * requests and responses.
 */
@Schema(description = "Customer profile, including contact details and loyalty program standing.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CustomerDTO implements HasIdentifier<Long> {

  @Schema(
      description = "Customer's mailing address.",
      example = "123 Le Loi, District 1, Ho Chi Minh City")
  private String address;

  /** Date of birth, used for birthday benefits; may be {@code null}. */
  @Schema(
      description = "Date of birth, used for birthday benefits; may be null.",
      example = "1995-08-20")
  private LocalDate dateOfBirth;

  @Schema(
      description = "Customer's email address; must be unique (BR-C01).",
      example = "customer@example.com")
  @NotBlank(message = "{CSM008}")
  @Email(message = "{CSM009}")
  private String email;

  @Schema(
      description = "Auto-assigned primary key.",
      example = "1",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  /** Response-only: current loyalty point balance, managed by the loyalty flow (BR-C03). */
  @Schema(
      description = "Current loyalty point balance, managed by the loyalty flow (BR-C03).",
      example = "1200",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Long loyaltyPoints;

  /** Response-only: current loyalty tier, derived from total spending (BR-C04). */
  @Schema(
      description = "Current loyalty tier, derived from total spending (BR-C04).",
      example = "SILVER",
      accessMode = Schema.AccessMode.READ_ONLY)
  private LoyaltyTier loyaltyTier;

  @Schema(description = "Customer's full name.", example = "Nguyen Van A")
  @NotBlank(message = "{CSM007}")
  private String name;

  @Schema(description = "Customer's phone number; may be null.", example = "0901234567")
  private String phone;

  /** Response-only: accumulated lifetime spending, managed by the loyalty flow (BR-C04). */
  @Schema(
      description = "Accumulated lifetime spending, managed by the loyalty flow (BR-C04).",
      example = "7500000",
      accessMode = Schema.AccessMode.READ_ONLY)
  private BigDecimal totalSpending;
}
