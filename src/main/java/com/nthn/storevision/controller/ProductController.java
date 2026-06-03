package com.nthn.storevision.controller;

import com.nthn.storevision.dto.ProductDTO;
import jakarta.validation.Valid;
import java.util.List;

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
   * Soft-deletes a product by ID.
   *
   * @param id the ID of the product to delete
   */
  void deleteProduct(Long id);

  /**
   * Retrieves all active products.
   *
   * @return list of all non-deleted products
   */
  List<ProductDTO> getAllProducts();

  /**
   * Retrieves a product by ID.
   *
   * @param id the ID of the product to retrieve
   * @return the matching product
   */
  ProductDTO getProductById(Long id);

  /**
   * Updates an existing product.
   *
   * @param id  the ID of the product to update
   * @param dto the updated product data
   * @return the updated product
   */
  ProductDTO updateProduct(Long id, @Valid ProductDTO dto);
}
