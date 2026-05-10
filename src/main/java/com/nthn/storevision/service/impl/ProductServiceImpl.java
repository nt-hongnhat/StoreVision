package com.nthn.storevision.service.impl;

import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.repository.ProductRepository;
import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.mapper.ProductMapper;
import com.nthn.storevision.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the {@link ProductService} interface.
 * Provides business logic for managing products.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductMapper productMapper;
  private final ProductRepository productRepository;

  /**
   * Retrieves a list of all products.
   *
   * @return A list of {@link ProductDTO} objects.
   */
  @Override
  @Transactional(readOnly = true)
  public List<ProductDTO> getAllProducts() {
    return productRepository.findAll().stream().map(productMapper::toDTO).toList();
  }

  /**
   * Retrieves a product by its unique identifier.
   *
   * @param id The ID of the product to retrieve.
   * @return The {@link ProductDTO} if found.
   * @throws RuntimeException if the product is not found.
   */
  @Override
  @Transactional(readOnly = true)
  public ProductDTO getProductById(final Long id) {
    final ProductEntity product =
        productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    return productMapper.toDTO(product);
  }

  /**
   * Creates a new product.
   *
   * @param productDTO The {@link ProductDTO} containing the details of the product to create.
   * @return The created {@link ProductDTO}.
   */
  @Override
  @Transactional
  public ProductDTO createProduct(final ProductDTO productDTO) {
    final ProductEntity product = productMapper.toEntity(productDTO);
    final ProductEntity savedProduct = productRepository.save(product);
    return productMapper.toDTO(savedProduct);
  }

  /**
   * Updates an existing product.
   *
   * @param id The ID of the product to update.
   * @param productDTO The {@link ProductDTO} containing the updated details.
   * @return The updated {@link ProductDTO}.
   * @throws RuntimeException if the product to update is not found.
   */
  @Override
  @Transactional
  public ProductDTO updateProduct(final Long id, final ProductDTO productDTO) {
    if (!productRepository.existsById(id)) {
      throw new RuntimeException("Product not found");
    }
    final ProductEntity product = productMapper.toEntity(productDTO);
    product.setId(id);
    final ProductEntity updatedProduct = productRepository.save(product);
    return productMapper.toDTO(updatedProduct);
  }

  /**
   * Deletes a product by its unique identifier.
   *
   * @param id The ID of the product to delete.
   */
  @Override
  @Transactional
  public void deleteProduct(final Long id) {
    productRepository.deleteById(id);
  }
}
