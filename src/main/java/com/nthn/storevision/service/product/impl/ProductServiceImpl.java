/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02
 */

package com.nthn.storevision.service.product.impl;

import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.mapper.ProductMapper;
import com.nthn.storevision.service.product.ProductCreationService;
import com.nthn.storevision.service.product.ProductDeletionService;
import com.nthn.storevision.service.product.ProductRetrievalService;
import com.nthn.storevision.service.product.ProductService;
import com.nthn.storevision.service.product.ProductUpdateService;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Aggregates all product operation services into a single entry point for the controller layer.
 * Handles DTO ↔ entity conversion and delegates to operation-specific services.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductCreationService creationService;
  private final ProductDeletionService deletionService;
  private final ProductMapper mapper;
  private final ProductRetrievalService retrievalService;
  private final ProductUpdateService updateService;

  @Override
  @Transactional
  public ProductDTO create(final ProductDTO dto) {
    log.debug("Creating product with name: {}", dto.getName());
    final ProductEntity entity = mapper.toEntity(dto);
    creationService.create(entity);
    log.debug("Product created with id: {}", entity.getId());
    return mapper.toDTO(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProductDTO> findAll() {
    log.debug("Fetching all products");
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
    updateService.update(entity);
    log.debug("Product id: {} updated successfully", id);
    return mapper.toDTO(entity);
  }

  @Override
  @Transactional
  public void delete(final Long id) {
    log.debug("Deleting product id: {}", id);
    deletionService.permanentlyDelete(id);
    log.debug("Product id: {} deleted", id);
  }
}
