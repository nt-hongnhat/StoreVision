/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:30:04
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.repository.generic.DeleteRepository;
import com.nthn.storevision.repository.generic.ReadRepository;
import com.nthn.storevision.repository.generic.SaveRepository;

/**
 * Repository interface for managing {@link ProductEntity} instances. Defines standard CRUD
 * operations for products.
 */
public interface ProductRepository
    extends ReadRepository<ProductEntity, Long>,
        SaveRepository<ProductEntity>,
        DeleteRepository<Long> {}
