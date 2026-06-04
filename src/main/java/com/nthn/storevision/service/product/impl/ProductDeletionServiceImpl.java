/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductDeletionServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04
 */

package com.nthn.storevision.service.product.impl;

import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.repository.ProductRepository;
import com.nthn.storevision.service.common.AbstractDeletable;
import com.nthn.storevision.service.product.ProductDeletionService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link ProductDeletionService} that soft-deletes {@link ProductEntity}
 * instances by setting the {@code isDeleted} flag, which triggers
 * {@code CustomAuditingEntityListener} to populate {@code deletedBy} and {@code deletedDate}.
 */
@Service
public class ProductDeletionServiceImpl
    extends AbstractDeletable<ProductEntity, Long, ProductRepository>
    implements ProductDeletionService {

  protected ProductDeletionServiceImpl(final ProductRepository repository) {
    super(repository);
  }
}
