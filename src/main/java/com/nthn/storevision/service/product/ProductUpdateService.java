/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductUpdateService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 21:14:30
 */

package com.nthn.storevision.service.product;

import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.service.generic.Updatable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for updating {@link ProductEntity} instances in the application.
 *
 * <p>This class implements the {@link Updatable} interface, providing functionality for persisting
 * updates to the {@link ProductEntity} objects in the underlying database. It uses a {@link
 * JpaRepository} for database access and is marked as a Spring {@link Service} to enable dependency
 * injection and manage its lifecycle. The {@code update} method is executed within a transactional
 * context, ensuring that changes are committed consistently.
 */
@Service
@RequiredArgsConstructor
public class ProductUpdateService implements Updatable<ProductEntity> {

  private final JpaRepository<ProductEntity, Long> repository;

  /**
   * Updates the provided {@link ProductEntity} object in the database.
   *
   * @param product The {@link ProductEntity} object to update.
   */
  @Transactional
  @Override
  public void update(final ProductEntity product) {
    repository.save(product);
  }
}
