/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductDeletionServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05
 */

package com.nthn.storevision.service.product.impl;

import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.repository.ProductRepository;
import com.nthn.storevision.service.common.AbstractDeletable;
import com.nthn.storevision.service.order.OrderRetrievalService;
import com.nthn.storevision.service.product.ProductDeletionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link ProductDeletionService} that soft-deletes {@link ProductEntity}
 * instances by setting the {@code isDeleted} flag, which triggers
 * {@code CustomAuditingEntityListener} to populate {@code deletedBy} and {@code deletedDate}.
 *
 * <p>BR-P04: if active PENDING orders reference this product, a warning is logged but the
 * soft-delete proceeds. Pending order items retain their original unit_price.
 */
@Slf4j
@Service
public class ProductDeletionServiceImpl
    extends AbstractDeletable<ProductEntity, Long, ProductRepository>
    implements ProductDeletionService {

  private final OrderRetrievalService orderRetrievalService;

  protected ProductDeletionServiceImpl(
      final ProductRepository repository,
      final OrderRetrievalService orderRetrievalService) {
    super(repository);
    this.orderRetrievalService = orderRetrievalService;
  }

  @Override
  @Transactional
  public void markAsDeleted(final Long id) {
    if (orderRetrievalService.existsPendingOrderForProduct(id)) {
      log.warn(
          "BR-P04: product id {} has PENDING orders — soft-deleting anyway;"
              + " pending order items retain their original unit_price.",
          id);
    }
    super.markAsDeleted(id);
  }
}
