package com.nthn.storevision.service;

import com.nthn.storevision.dto.ProductDTO;
import java.util.List;

/**
 * Service interface for managing product-related business logic.
 * Defines the contract for product CRUD operations.
 */
public interface ProductService {
    /**
     * Retrieves a list of all products.
     *
     * @return A list of {@link ProductDTO} objects.
     */
    List<ProductDTO> getAllProducts();

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param id The ID of the product to retrieve.
     * @return The {@link ProductDTO} if found.
     * @throws com.nthn.storevision.exception.ResourceNotFoundException if the product is not found.
     */
    ProductDTO getProductById(Long id);

    /**
     * Creates a new product.
     *
     * @param productDTO The {@link ProductDTO} containing the details of the product to create.
     * @return The created {@link ProductDTO}.
     */
    ProductDTO createProduct(ProductDTO productDTO);

    /**
     * Updates an existing product.
     *
     * @param id The ID of the product to update.
     * @param productDTO The {@link ProductDTO} containing the updated details.
     * @return The updated {@link ProductDTO}.
     * @throws com.nthn.storevision.exception.ResourceNotFoundException if the product to update is not found.
     */
    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    /**
     * Deletes a product by its unique identifier.
     *
     * @param id The ID of the product to delete.
     * @throws com.nthn.storevision.exception.ResourceNotFoundException if the product to delete is not found.
     */
    void deleteProduct(Long id);
}
