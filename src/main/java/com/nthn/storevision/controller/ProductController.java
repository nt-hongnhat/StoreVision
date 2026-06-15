/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductController.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 11:37:15
 */

package com.nthn.storevision.controller;

import com.nthn.storevision.dto.product.ProductDTO;
import com.nthn.storevision.dto.request.ProductRestoreRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

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
   * Deletes a product by ID. By default, soft-deletes the product, emitting a warning if PENDING
   * orders reference it (BR-P04). If {@code hard} is {@code true}, permanently removes the record
   * from the database instead (Admin only).
   *
   * @param id the ID of the product to delete
   * @param hard {@code true} to permanently delete the record; {@code false} to soft-delete it
   */
  void deleteProduct(Long id, boolean hard);

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
  Page<ProductDTO> getAllProducts(int page, int size, String name, String category);

  /**
   * Retrieves a product by ID.
   *
   * @param id the ID of the product to retrieve
   * @return the matching product
   */
  ProductDTO getProductById(Long id);

  /**
   * Restores a previously soft-deleted product by clearing its soft-delete markers.
   *
   * @param id the ID of the product to restore
   * @param request the restore request; {@code deleted} must be {@code false} — use {@code DELETE
   *     /products/{id}} to soft-delete a product instead
   * @return the restored product
   */
  ProductDTO restoreProduct(Long id, @Valid ProductRestoreRequest request);

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
