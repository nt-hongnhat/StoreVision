/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductRetrievalServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 16:40:34
 */

package com.nthn.storevision.service.product.impl;

import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.mapper.ProductMapper;
import com.nthn.storevision.repository.ProductRepository;
import com.nthn.storevision.service.common.AbstractReadable;
import com.nthn.storevision.service.product.ProductRetrievalService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for retrieving {@link ProductEntity} entities. Extends {@link AbstractReadable} to
 * provide read operations for {@link ProductEntity} entities.
 */
@Slf4j
@Service
public class ProductRetrievalServiceImpl
    extends AbstractReadable<ProductEntity, Long, ProductRepository>
    implements ProductRetrievalService {

  private final ProductMapper productMapper;

  /**
   * Constructs a {@code ProductRetrievalServiceImpl} with the specified repository.
   *
   * @param repository the repository used for accessing {@link ProductEntity} entities
   * @param productMapper the mapper used for converting between entity and DTO
   */
  public ProductRetrievalServiceImpl(
      final ProductRepository repository, final ProductMapper productMapper) {
    super(repository);
    this.productMapper = productMapper;
  }

  /**
   * Retrieves all products and maps them to {@link ProductDTO}.
   *
   * @return a list of all {@link ProductDTO}s.
   */
  @Override
  @Transactional(readOnly = true)
  public List<ProductDTO> findAllProductsAsDTO() {
    log.debug("Fetching all products as DTO");
    return findAll(productMapper::toDTO);
  }

  /**
   * Retrieves a product by its identifier and maps it to {@link ProductDTO}.
   *
   * @param id the identifier of the product to be retrieved.
   * @return the {@link ProductDTO} with the specified identifier, or null if not found.
   */
  @Override
  @Transactional(readOnly = true)
  public ProductDTO findProductDTOById(final Long id) {
    log.debug("Fetching product DTO by id: {}", id);
    return findById(id, productMapper::toDTO);
  }

  /**
   * Returns the message code for when a product is not found.
   *
   * @return The message code for "product not found".
   */
  @Override
  protected String getNotFoundMessageCode() {
    // Assuming a constant for product not found message exists, similar to
    // UserMessage.USER_NOT_FOUND
    // If not, you might need to create one or use a generic message.
    return "product.not.found"; // Placeholder, replace it with actual message code if available
  }
}
