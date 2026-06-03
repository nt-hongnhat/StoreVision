/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 11:21:37
 */

package com.nthn.storevision.service.product;

import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.service.generic.Creatable;

/**
 * Service interface for creating and persisting {@link ProductEntity} entities.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see Creatable
 */
public interface ProductCreationService extends Creatable<ProductEntity, Long> {}
