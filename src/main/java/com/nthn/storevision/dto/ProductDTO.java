/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:19:12
 */

package com.nthn.storevision.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.*;

/**
 * Data Transfer Object for Product. Used to transfer product data between layers, often for API
 * requests and responses.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
  private Long id;

  @NotBlank(message = "{product.name.required}")
  private String name;

  @NotNull(message = "{product.price.required}")
  @Min(value = 0, message = "{product.price.min}")
  private BigDecimal price;

  @NotNull(message = "{product.stock.required}")
  @Min(value = 0, message = "{product.stock.min}")
  private Integer stockQuantity;
}
