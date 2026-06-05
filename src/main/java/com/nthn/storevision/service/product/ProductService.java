/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02
 */

package com.nthn.storevision.service.product;

import com.nthn.storevision.dto.ProductDTO;
import java.util.List;

/** Single entry point for all product business operations used by the controller layer. */
public interface ProductService {

  ProductDTO create(ProductDTO dto);

  List<ProductDTO> findAll();

  ProductDTO findById(Long id);

  ProductDTO update(Long id, ProductDTO dto);

  /** Soft-deletes the product. Emits a warning (BR-P04) if active PENDING orders exist. */
  void delete(Long id);

  /** Permanently removes the product from the database (Admin only). */
  void permanentlyDelete(Long id);

  /** Restores a previously soft-deleted product. */
  void restore(Long id);

  /**
   * Searches active products by optional name (case-insensitive) and optional category.
   * Passing {@code null} omits that filter.
   */
  List<ProductDTO> search(String name, String category);
}
