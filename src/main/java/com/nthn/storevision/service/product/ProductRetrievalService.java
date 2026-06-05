/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductRetrievalService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 21:12:35
 */

package com.nthn.storevision.service.product;

import com.nthn.storevision.dto.ProductDTO;
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
   * Retrieves a product by its identifier and maps it to {@link ProductDTO}.
   *
   * @param id the identifier of the product to be retrieved.
   * @return the {@link ProductDTO} with the specified identifier, or null if not found.
   */
  ProductDTO findProductDTOById(Long id);

  /**
   * Searches active products by optional name (case-insensitive contains) and optional category
   * (exact match). Passing {@code null} for either parameter omits that filter.
   *
   * @param name     partial product name to match (case-insensitive), or {@code null} to skip
   * @param category exact category to match, or {@code null} to skip
   * @return list of matching active {@link ProductDTO}s
   */
  List<ProductDTO> search(String name, String category);
}
