/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 16:15:24
 */

package com.nthn.storevision.service.product.impl;

import com.nthn.storevision.entity.ProductEntity;
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
    extends AbstractCreatable<ProductEntity, Long, ProductEntity>
    implements ProductCreationService {

  public ProductCreationServiceImpl(final ProductRepository repository) {
    super(repository::save);
  }
}
