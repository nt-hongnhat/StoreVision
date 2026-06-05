/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 15:24:59
 */

package com.nthn.storevision.service.product.impl;

import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.service.order.OrderRetrievalService;
import com.nthn.storevision.service.product.ProductCreationService;
import com.nthn.storevision.service.product.ProductDeletionService;
import com.nthn.storevision.service.product.ProductRetrievalService;
import com.nthn.storevision.service.product.ProductService;
import com.nthn.storevision.service.product.ProductUpdateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Aggregates all product operation services into a single entry point for the controller layer.
 * Enforces BR-P04 (warning on soft-delete with pending orders) and delegates all business logic
 * to operation-specific services.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductCreationService creationService;
  private final ProductDeletionService deletionService;
  private final OrderRetrievalService orderRetrievalService;
  private final ProductRetrievalService retrievalService;
  private final ProductUpdateService updateService;

  @Override
  @Transactional
  public ProductDTO create(final @NonNull ProductDTO dto) {
    log.debug("Creating product with name: {}", dto.getName());
    final ProductDTO created = creationService.create(dto);
    log.debug("Product created with id: {}", created.getId());
    return created;
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProductDTO> findAll() {
    log.debug("Fetching all active products");
    return retrievalService.findAllProductsAsDTO();
  }

  @Override
  @Transactional(readOnly = true)
  public ProductDTO findById(final Long id) {
    log.debug("Fetching product by id: {}", id);
    return retrievalService.findProductDTOById(id);
  }

  @Override
  @Transactional
  public ProductDTO update(final Long id, final ProductDTO dto) {
    log.debug("Updating product id: {}", id);
    final ProductEntity entity = retrievalService.findById(id, Function.identity());
    entity.setName(dto.getName());
    entity.setPrice(dto.getPrice());
    entity.setStockQuantity(dto.getStockQuantity());
    entity.setCategory(dto.getCategory());
    updateService.update(entity.getId(), dto);
    log.debug("Product id: {} updated successfully", id);
    return productMapper.toDTO(entity);
  }

  /**
   * Soft-deletes a product. BR-P04: if any PENDING order references this product, a warning is
   * logged but the soft-delete proceeds. Pending order items retain their original unit_price.
   */
  @Override
  @Transactional
  public void delete(final Long id) {
    log.debug("Soft-deleting product id: {}", id);
    if (orderRetrievalService.existsPendingOrderForProduct(id)) {
      log.warn(
          "BR-P04: product id {} has PENDING orders — soft-deleting anyway;"
              + " pending order items retain their original unit_price.",
          id);
    }
    deletionService.markAsDeleted(id);
    log.debug("Product id: {} marked as deleted", id);
  }

  @Override
  @Transactional
  public void permanentlyDelete(final Long id) {
    log.debug("Permanently deleting product id: {}", id);
    deletionService.permanentlyDelete(id);
    log.debug("Product id: {} permanently deleted", id);
  }

  @Override
  @Transactional
  public void restore(final Long id) {
    log.debug("Restoring product id: {}", id);
    deletionService.restore(id);
    log.debug("Product id: {} restored", id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProductDTO> search(final String name, final String category) {
    log.debug("Searching products — name: {}, category: {}", name, category);
    return retrievalService.search(name, category);
  }
}
