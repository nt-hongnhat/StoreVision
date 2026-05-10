package com.nthn.storevision.repository;

import com.nthn.storevision.entity.ProductEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link ProductEntity} instances.
 * Defines standard CRUD operations for products.
 */
public interface ProductRepository {
  /**
   * Deletes a product by its ID.
   *
   * @param id The ID of the product to delete.
   */
  void deleteById(Long id);

  /**
   * Checks if a product with the given ID exists.
   *
   * @param id The ID of the product to check.
   * @return {@code true} if a product with the given ID exists, {@code false} otherwise.
   */
  boolean existsById(Long id);

  /**
   * Retrieves all products.
   *
   * @return A list of all {@link ProductEntity} instances.
   */
  List<ProductEntity> findAll();

  /**
   * Retrieves a product by its ID.
   *
   * @param id The ID of the product to retrieve.
   * @return An {@link Optional} containing the {@link ProductEntity} if found, or empty if not.
   */
  Optional<ProductEntity> findById(Long id);

  /**
   * Saves a product entity.
   *
   * @param product The {@link ProductEntity} to save.
   * @return The saved {@link ProductEntity}.
   */
  ProductEntity save(ProductEntity product);
}
