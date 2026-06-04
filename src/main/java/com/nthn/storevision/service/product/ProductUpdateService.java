/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductUpdateService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 11:21:37
 */

package com.nthn.storevision.service.product;

import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.service.generic.Updatable;

/**
 * Service interface for updating products.
 *
 * @see Updatable
 */
@FunctionalInterface
public interface ProductUpdateService extends Updatable<ProductDTO> {}
