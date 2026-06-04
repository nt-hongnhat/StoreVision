/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 17:15:08
 */

package com.nthn.storevision.service.product.impl;

import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.repository.ProductRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.product.ProductCreationService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link ProductCreationService} that extends {@link AbstractCreatable}
 * to persist {@link ProductEntity} instances via {@link ProductRepository}.
 */
@Service
public class ProductCreationServiceImpl
    extends AbstractCreatable<ProductDTO, ProductEntity, ProductRepository>
    implements ProductCreationService {

  protected ProductCreationServiceImpl(
      final EntityMapperConfig<ProductDTO, ?, ProductEntity> mapper, final ProductRepository repository) {
    super(mapper, repository);
  }
}
