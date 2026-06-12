/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductRetrievalServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:25:29
 */

package com.nthn.storevision.service.product.impl;

import com.nthn.storevision.constant.message.LocalizedMessageCode;
import com.nthn.storevision.constant.message.ProductMessageCode;
import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.dto.response.PageDTO;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.mapper.ProductMapper;
import com.nthn.storevision.repository.ProductRepository;
import com.nthn.storevision.service.common.AbstractReadable;
import com.nthn.storevision.service.product.ProductRetrievalService;
import com.nthn.storevision.util.PaginationUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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

  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "name");

  private final ProductMapper productMapper;

  public ProductRetrievalServiceImpl(
      final ProductRepository repository, final ProductMapper productMapper) {
    super(repository);
    this.productMapper = productMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProductDTO> findAllProductsAsDTO() {
    log.debug("Fetching all active products as DTO");
    return getRepository().findAllActive().stream().map(productMapper::toDTO).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProductDTO> findLowStockProducts(final int threshold) {
    log.debug("Fetching active products with stockQuantity <= {}", threshold);
    return getRepository().findLowStock(threshold).stream().map(productMapper::toDTO).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public ProductDTO findProductDTOById(final Long id) {
    log.debug("Fetching product DTO by id: {}", id);
    return findById(id, productMapper::toDTO);
  }

  @Override
  @Transactional(readOnly = true)
  public PageDTO<ProductDTO> findProductsPage(final int page, final int size) {
    log.debug("Fetching products page: page={}, size={}", page, size);
    return PageDTO.from(
        getRepository()
            .findAllActive(PaginationUtil.toPageRequest(page, size, DEFAULT_SORT))
            .map(productMapper::toDTO));
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProductDTO> search(final String name, final String category) {
    log.debug("Searching active products — name: {}, category: {}", name, category);
    return getRepository().searchActive(name, category).stream().map(productMapper::toDTO).toList();
  }

  @Override
  protected LocalizedMessageCode getNotFoundMessageCode() {
    return ProductMessageCode.NOT_FOUND;
  }
}
