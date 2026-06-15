/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: ProductCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:40:22
 */

package com.nthn.storevision.service.product.impl;

import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.exception.product.ProductNameAlreadyExistsException;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.repository.ProductRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.product.ProductCreationService;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link ProductCreationService}. Enforces BR-P01 (name + category
 * uniqueness among active records) before delegating persistence to {@link AbstractCreatable}.
 */
@Slf4j
@Service
public class ProductCreationServiceImpl
    extends AbstractCreatable<ProductDTO, ProductEntity, ProductRepository>
    implements ProductCreationService {

  protected ProductCreationServiceImpl(
      final EntityMapperConfig<ProductDTO, ?, ProductEntity> mapper,
      final ProductRepository repository) {
    super(mapper, repository);
  }

  @Override
  public ProductDTO create(final @NonNull ProductDTO dto) {
    if (getRepository().isProductActiveAndInCategory(dto.getName(), dto.getCategory())) {
      log.warn(
          "BR-P01 violation: product '{}' already exists in category '{}'",
          dto.getName(),
          dto.getCategory());
      throw new ProductNameAlreadyExistsException(dto.getName(), dto.getCategory());
    }
    return super.create(dto);
  }
}
