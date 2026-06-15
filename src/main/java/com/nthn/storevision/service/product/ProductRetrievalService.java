/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductRetrievalService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 10:57:57
 */

package com.nthn.storevision.service.product;

import com.nthn.storevision.dto.product.ProductDTO;
import com.nthn.storevision.dto.response.PageDTO;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.service.generic.Readable;
import java.util.List;

/**
 * Service interface for retrieving {@link ProductEntity} entities.
 *
 * <p>Extends the {@link Readable} interface to provide common read operations for {@link
 * ProductEntity} entities.
 */
public interface ProductRetrievalService extends Readable<ProductEntity, Long> {

  /**
   * Retrieves all products and maps them to {@link ProductDTO}.
   *
   * @return a list of all {@link ProductDTO}s.
   */
  List<ProductDTO> findAllProductsAsDTO();

  /**
   * Returns active products whose stock quantity is at or below {@code threshold}. Used by the
   * inventory module for BR-I04 low-stock alerts (threshold typically {@code 10}).
   *
   * @param threshold inclusive upper bound for stock quantity
   * @return list of low-stock active {@link ProductDTO}s
   */
  List<ProductDTO> findLowStockProducts(int threshold);

  /**
   * Retrieves a product by its identifier and maps it to {@link ProductDTO}.
   *
   * @param id the identifier of the product to be retrieved.
   * @return the {@link ProductDTO} with the specified identifier, or null if not found.
   */
  ProductDTO findProductDTOById(Long id);

  /**
   * Retrieves one page of active products ordered by name, optionally filtered by name
   * (case-insensitive contains) and/or category (exact match), mapped to {@link ProductDTO}.
   * Out-of-range pagination parameters are clamped instead of rejected. Passing {@code null} for
   * {@code name} or {@code category} omits that filter.
   *
   * @param page zero-based page index; negative values are treated as 0
   * @param size page size; clamped to a sane maximum
   * @param name partial product name to match (case-insensitive), or {@code null} to skip
   * @param category exact category to match, or {@code null} to skip
   * @return the requested page of matching products; never {@code null}
   */
  PageDTO<ProductDTO> findProductsPage(int page, int size, String name, String category);
}
