/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 13:55:53
 */

package com.nthn.storevision.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.*;

/**
 * Data Transfer Object for Product. Used for both API request payloads and response bodies.
 * Response-only fields (id, createdBy, createdDate, lastModifiedDate) are null in request context.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

  /** Response-only: dynamic product attributes stored as JSONB (e.g. {"color":"Đỏ","size":"XL"}). */
  private Map<String, String> attributes;

  private String category;

  /** Response-only: email of the user who created this product. */
  private String createdBy;

  /** Response-only: timestamp when this product was created. */
  private LocalDateTime createdDate;

  private String description;

  /** Response-only: auto-assigned primary key. */
  private Long id;

  /** Response-only: timestamp of the last modification. */
  private LocalDateTime lastModifiedDate;

  @NotBlank(message = "{PRM003}")
  private String name;

  /** BR-P02: price must be strictly greater than zero. */
  @NotNull(message = "{PRM004}")
  @DecimalMin(value = "0.01", message = "{PRM005}")
  private BigDecimal price;

  /** Unique stock-keeping unit code; optional on creation. */
  private String sku;

  /** BR-P03: stock quantity must be non-negative. */
  @NotNull(message = "{PRM006}")
  @Min(value = 0, message = "{PRM007}")
  private Integer stockQuantity;
}
