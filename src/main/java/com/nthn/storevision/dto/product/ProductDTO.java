/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: ProductDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:48:32
 */

package com.nthn.storevision.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Product catalog entry, including pricing, stock and dynamic attributes.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

  /** Response-only: dynamic product attributes stored as JSONB (e.g. {"color":"Đỏ","size":"XL"}). */
  @Schema(
      description = "Dynamic product attributes stored as JSONB (e.g. {\"color\":\"Red\",\"size\":\"XL\"}).",
      example = "{\"color\": \"Red\", \"size\": \"XL\"}",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Map<String, String> attributes;

  @Schema(description = "Category the product belongs to.", example = "Electronics")
  private String category;

  /** Response-only: email of the user who created this product. */
  @Schema(
      description = "Email of the user who created this product.",
      example = "admin@example.com",
      accessMode = Schema.AccessMode.READ_ONLY)
  private String createdBy;

  /** Response-only: timestamp when this product was created. */
  @Schema(
      description = "Timestamp when this product was created.",
      example = "2026-05-01T09:00:00",
      accessMode = Schema.AccessMode.READ_ONLY)
  private LocalDateTime createdDate;

  @Schema(
      description = "Free-text description of the product.",
      example = "Ergonomic wireless mouse with adjustable DPI.")
  private String description;

  /** Response-only: auto-assigned primary key. */
  @Schema(
      description = "Auto-assigned primary key.",
      example = "1",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  /** Response-only: timestamp of the last modification. */
  @Schema(
      description = "Timestamp of the last modification.",
      example = "2026-06-10T15:20:00",
      accessMode = Schema.AccessMode.READ_ONLY)
  private LocalDateTime lastModifiedDate;

  @Schema(description = "Display name of the product; must be unique within its category (BR-P01).", example = "Wireless Mouse")
  @NotBlank(message = "{PRM003}")
  private String name;

  /** BR-P02: price must be strictly greater than zero. */
  @Schema(description = "Selling price of the product; must be greater than zero (BR-P02).", example = "299000")
  @NotNull(message = "{PRM004}")
  @DecimalMin(value = "0.01", message = "{PRM005}")
  private BigDecimal price;

  /** Unique stock-keeping unit code; optional on creation. */
  @Schema(description = "Unique stock-keeping unit code; optional on creation.", example = "WM-001")
  private String sku;

  /** BR-P03: stock quantity must be non-negative. */
  @Schema(description = "Current units in stock; must not be negative (BR-P03).", example = "50")
  @NotNull(message = "{PRM006}")
  @Min(value = 0, message = "{PRM007}")
  private Integer stockQuantity;
}
