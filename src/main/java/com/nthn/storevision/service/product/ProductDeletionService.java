/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductDeletionService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 21:15:33
 */

package com.nthn.storevision.service.product;

import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.repository.jpa.JpaProductRepository;
import com.nthn.storevision.service.generic.HardDeletable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for permanently deleting {@link ProductEntity} entities.
 * This class implements the {@link HardDeletable} interface.
 */
@Service
@RequiredArgsConstructor
public class ProductDeletionService implements HardDeletable<ProductEntity, Long> {

  private final JpaProductRepository repository;

  /**
   * Deletes an existing product entity.
   *
   * @param entity The {@link ProductEntity} to be deleted.
   */
  @Override
  @Transactional
  public void delete(final ProductEntity entity) {
    repository.delete(entity);
  }

  /**
   * Permanently deletes a product entity by its identifier.
   *
   * @param id The ID of the product to be deleted.
   */
  @Override
  @Transactional
  public void permanentlyDelete(final Long id) {
    repository.deleteById(id);
  }
}
