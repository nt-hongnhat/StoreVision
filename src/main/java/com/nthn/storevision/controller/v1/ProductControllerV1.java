/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:10:30
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.mapper.ProductMapper;
import com.nthn.storevision.service.product.ProductCreationService;
import com.nthn.storevision.service.product.ProductDeletionService;
import com.nthn.storevision.service.product.ProductRetrievalService;
import com.nthn.storevision.service.product.ProductUpdateService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing products.
 * Provides endpoints for CRUD operations on products.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductControllerV1 {

  private final ProductCreationService productCreationService;
  private final ProductDeletionService productDeletionService;
  private final ProductMapper productMapper;
  private final ProductRetrievalService productRetrievalService;
  private final ProductUpdateService productUpdateService;

  /**
   * Creates a new product.
   *
   * @param productDTO The ProductDTO containing the details of the product to create.
   * @return A ResponseEntity containing the created ProductDTO.
   */
  @PostMapping
  public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody final ProductDTO productDTO) {
    log.debug("POST /products");
    final ProductEntity productEntity = productMapper.toEntity(productDTO);
    productCreationService.create(productEntity);
    return new ResponseEntity<>(productMapper.toDTO(productEntity), HttpStatus.CREATED);
  }

  /**
   * Deletes a product by its unique identifier.
   *
   * @param id The ID of the product to delete.
   * @return A ResponseEntity with no content if deletion is successful.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
    log.debug("DELETE /products/{}", id);
    productDeletionService.permanentlyDelete(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Retrieves a list of all products.
   *
   * @return A ResponseEntity containing a list of ProductDTOs.
   */
  @GetMapping
  public ResponseEntity<List<ProductDTO>> getAllProducts() {
    log.debug("GET /products");
    return ResponseEntity.ok(productRetrievalService.findAllProductsAsDTO());
  }

  /**
   * Retrieves a product by its unique identifier.
   *
   * @param id The ID of the product to retrieve.
   * @return A ResponseEntity containing the ProductDTO if found, or a not found status.
   */
  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> getProductById(@PathVariable final Long id) {
    log.debug("GET /products/{}", id);
    return ResponseEntity.ok(productRetrievalService.findProductDTOById(id));
  }

  /**
   * Updates an existing product.
   *
   * @param id The ID of the product to update.
   * @param productDTO The ProductDTO containing the updated details.
   * @return A ResponseEntity containing the updated ProductDTO.
   */
  @PutMapping("/{id}")
  public ResponseEntity<ProductDTO> updateProduct(@PathVariable final Long id, @Valid @RequestBody final ProductDTO productDTO) {
    log.debug("PUT /products/{}", id);
    final ProductEntity existingProduct = productMapper.toEntity(productRetrievalService.findProductDTOById(id));
    existingProduct.setName(productDTO.getName());
    existingProduct.setPrice(productDTO.getPrice());
    existingProduct.setStockQuantity(productDTO.getStockQuantity());
    existingProduct.setCategory(productDTO.getCategory()); // Add category update
    productUpdateService.update(existingProduct);
    return ResponseEntity.ok(productMapper.toDTO(existingProduct));
  }
}
