package com.nthn.storevision.controller;

import com.nthn.storevision.dto.ProductDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 * Interface for managing product-related operations. Defines the contract for product CRUD
 * operations via REST endpoints.
 */
public interface ProductController {
  /**
   * Creates a new product.
   *
   * @param productDTO The {@link ProductDTO} containing the details of the product to create.
   * @return A {@link ResponseEntity} containing the created {@link ProductDTO}.
   */
  ResponseEntity<ProductDTO> createProduct(ProductDTO productDTO);

  /**
   * Deletes a product by its unique identifier.
   *
   * @param id The ID of the product to delete.
   * @return A {@link ResponseEntity} with no content if deletion is successful.
   */
  ResponseEntity<Void> deleteProduct(Long id);

  /**
   * Retrieves a list of all products.
   *
   * @return A {@link ResponseEntity} containing a list of ProductDTOs.
   */
  ResponseEntity<List<ProductDTO>> getAllProducts();

  /**
   * Retrieves a product by its unique identifier.
   *
   * @param id The ID of the product to retrieve.
   * @return A {@link ResponseEntity} containing the {@link ProductDTO} if found, or a not found
   *     status.
   */
  ResponseEntity<ProductDTO> getProductById(Long id);

  /**
   * Updates an existing product.
   *
   * @param id The ID of the product to update.
   * @param productDTO The {@link ProductDTO} containing the updated details.
   * @return A {@link ResponseEntity} containing the updated {@link ProductDTO}.
   */
  ResponseEntity<ProductDTO> updateProduct(Long id, ProductDTO productDTO);
}
