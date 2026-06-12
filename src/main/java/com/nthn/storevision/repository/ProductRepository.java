/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 15:43:24
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.repository.generic.DeleteRepository;
import com.nthn.storevision.repository.generic.ReadRepository;
import com.nthn.storevision.repository.generic.SaveRepository;
import java.util.List;

/**
 * Repository interface for managing {@link ProductEntity} instances. Defines standard CRUD
 * operations and product-specific queries for products.
 */
public interface ProductRepository
    extends ReadRepository<ProductEntity, Long>,
        SaveRepository<ProductEntity>,
        DeleteRepository<Long> {

  /**
   * BR-O01: atomically decrements a product's stock by {@code quantity} only when enough stock
   * remains — concurrent order completions can never drive the stock negative.
   *
   * @param id the product to update; must not be {@code null}
   * @param quantity units to deduct; must be positive
   * @return {@code true} when the stock was deducted; {@code false} when the product does not exist
   *     or the remaining stock is not enough
   */
  boolean decreaseStockIfAvailable(Long id, Integer quantity);

  /**
   * Retrieves a list of all active (non-deleted) products.
   *
   * @return a list of {@code ProductEntity} objects representing the active products.
   */
  List<ProductEntity> findAllActive();

   * Atomically increments a product's stock by {@code quantity}, preventing lost updates from
   * concurrent receipts.
   *
   * @param id the product to update; must not be {@code null}
   * @param quantity units to add; must be positive
   * @return {@code true} when the product was updated; {@code false} when it does not exist
   */
  boolean increaseStock(Long id, Integer quantity);

  /**
   * Checks if an active (non-deleted) product with the specified name and category exists.
   *
   * @param name the name of the product to check for.
   * @param category the category of the product to check for.
   * @return {@code true} if an active product with the given name and category exists;
   *         {@code false} otherwise.
   */
  boolean isProductActiveAndInCategory(String name, String category);

  /**
   * Checks if an active (non-deleted) product with the specified name and category exists,
   * excluding the product with the given ID.
   *
   * @param name the name of the product to check for.
   * @param category the category of the product to check for.
   * @param id the ID of the product to exclude from the check.
   * @return {@code true} if an active product with the given name and category exists, excluding
   *         the product with the specified ID; {@code false} otherwise.
   */
  boolean isProductActiveInCategoryExcludingId(String name, String category, Long id);

  /**
   * Searches for active (non-deleted) products based on the specified name and category.
   *
   * @param name the name of the product to search for; can be a partial or complete match.
   * @param category the category of the product to search for.
   * @return a list of {@code ProductEntity} objects that match the search criteria and are active.
   */
  List<ProductEntity> searchActive(String name, String category);
}
