/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 15:25:45
 */

package com.nthn.storevision.service.product.impl;

import com.nthn.storevision.constant.ProductMessageCodes;
import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.EntityMapper;
import com.nthn.storevision.repository.ProductRepository;
import com.nthn.storevision.service.common.AbstractUpdatable;
import com.nthn.storevision.service.product.ProductUpdateService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link ProductUpdateService} for persisting updates to {@link
 * ProductEntity} instances in the database.
 */
@Service
public class ProductUpdateServiceImpl
    extends AbstractUpdatable<ProductDTO, Long, ProductEntity, ProductRepository>
    implements ProductUpdateService {

  protected ProductUpdateServiceImpl(
      final EntityMapper<ProductDTO, Long, ProductEntity> mapper,
      final ProductRepository repository) {
    super(mapper, repository);
  }

  @Override
  protected ProductEntity getById(final Long id) {
    return getRepository()
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(ProductMessageCodes.NOT_FOUND, id));
  }
}
