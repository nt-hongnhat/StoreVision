/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductDeletionService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 11:21:37
 */

package com.nthn.storevision.service.product;

import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.service.generic.HardDeletable;
import com.nthn.storevision.service.generic.SoftDeletable;

/**
 * Service interface for soft-deleting, restoring, and permanently deleting
 * {@link ProductEntity} entities.
 *
 * @see SoftDeletable
 * @see HardDeletable
 */
public interface ProductDeletionService
    extends SoftDeletable<ProductEntity, Long>, HardDeletable<ProductEntity, Long> {}
