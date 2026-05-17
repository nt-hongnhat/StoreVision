/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 20:36:11
 */

package com.nthn.storevision.service.product;

import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.generic.Creatable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Service for creating and persisting {@link ProductEntity} entities. This class extends {@link
 * AbstractCreatable} to provide a generic implementation of the {@link Creatable} interface for
 * {@link ProductEntity} entities. It uses a {@link JpaRepository} to manage database interactions.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see com.nthn.storevision.service.generic.Creatable
 * @see JpaRepository
 */
@Service
public class ProductCreationService extends AbstractCreatable<ProductEntity, Long, ProductEntity>
    implements Creatable<ProductEntity, Long> {

  /**
   * Constructs a {@code ProductCreationService} with the specified repository for managing {@link
   * ProductEntity} instances.
   *
   * @param repository the repository used for persisting and managing {@link ProductEntity} instances
   */
  public ProductCreationService(final JpaRepository<ProductEntity, Long> repository) {
    super(repository);
  }
}
