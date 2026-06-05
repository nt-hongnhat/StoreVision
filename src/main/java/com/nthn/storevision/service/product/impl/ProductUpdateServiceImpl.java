/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 15:43:24
 */

package com.nthn.storevision.service.product.impl;

import com.nthn.storevision.constant.ProductMessageCodes;
import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.exception.ProductNameAlreadyExistsException;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.repository.ProductRepository;
import com.nthn.storevision.service.common.AbstractUpdatable;
import com.nthn.storevision.service.product.ProductUpdateService;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link ProductUpdateService}. Enforces BR-P01 (name + category
 * uniqueness, excluding the record being updated) before delegating to {@link AbstractUpdatable}.
 */
@Slf4j
@Service
public class ProductUpdateServiceImpl
    extends AbstractUpdatable<ProductDTO, Long, ProductEntity, ProductRepository>
    implements ProductUpdateService {

  protected ProductUpdateServiceImpl(
      final EntityMapperConfig<ProductDTO, Long, ProductEntity> mapper,
      final ProductRepository repository) {
    super(mapper, repository);
  }

  @Override
  public ProductDTO update(final Long id, final @NonNull ProductDTO dto) {
    if (getRepository()
        .isProductActiveInCategoryExcludingId(dto.getName(), dto.getCategory(), id)) {
      log.warn(
          "BR-P01 violation on update: product '{}' already exists in category '{}'",
          dto.getName(),
          dto.getCategory());
      throw new ProductNameAlreadyExistsException(dto.getName(), dto.getCategory());
    }

    return super.update(id, dto);
  }

  @Override
  protected ProductEntity getById(final Long id) {
    return getRepository()
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(ProductMessageCodes.NOT_FOUND, id));
  }
}
