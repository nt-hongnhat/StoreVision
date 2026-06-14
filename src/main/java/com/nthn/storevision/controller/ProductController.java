/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductController.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:28:06
 */

package com.nthn.storevision.controller;

import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.dto.response.PageDTO;
import jakarta.validation.Valid;

/**
 * API contract for product management endpoints.
 *
 * @see com.nthn.storevision.controller.v1.ProductControllerV1
 */
public interface ProductController {

  /**
   * Creates a new product.
   *
   * @param dto the product data to persist
   * @return the created product
   */
  ProductDTO createProduct(@Valid ProductDTO dto);

  /**
   * Soft-deletes a product by ID. Emits a warning if PENDING orders reference it (BR-P04).
   *
   * @param id the ID of the product to soft-delete
   */
  void deleteProduct(Long id);

  /**
   * Retrieves one page of active (non-deleted) products ordered by name, optionally filtered by
   * name (case-insensitive contains) and/or category (exact match).
   *
   * @param page zero-based page index
   * @param size page size
   * @param name partial product name filter, or {@code null} to skip
   * @param category exact category filter, or {@code null} to skip
   * @return the requested page of products
   */
  PageDTO<ProductDTO> getAllProducts(int page, int size, String name, String category);

  /**
   * Retrieves a product by ID.
   *
   * @param id the ID of the product to retrieve
   * @return the matching product
   */
  ProductDTO getProductById(Long id);

  /**
   * Permanently removes a product from the database (Admin only).
   *
   * @param id the ID of the product to permanently delete
   */
  void permanentlyDeleteProduct(Long id);

  /**
   * Restores a previously soft-deleted product.
   *
   * @param id the ID of the product to restore
   */
  void restoreProduct(Long id);

  /**
   * Updates an existing product (name, price, stockQuantity, category, description, sku,
   * attributes). Enforces BR-P01 (name + category uniqueness).
   *
   * @param id the ID of the product to update
   * @param dto the updated product data
   * @return the updated product
   */
  ProductDTO updateProduct(Long id, @Valid ProductDTO dto);
}
