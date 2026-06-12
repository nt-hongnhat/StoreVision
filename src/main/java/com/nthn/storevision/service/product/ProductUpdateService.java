/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductUpdateService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 16:40:51
 */

package com.nthn.storevision.service.product;

import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.service.generic.Updatable;

/**
 * Service interface for updating products.
 *
 * @see Updatable
 */
public interface ProductUpdateService extends Updatable<ProductDTO, Long> {

  /**
   * Increments the stock quantity of the given product by {@code quantity}. Used by the inventory
   * module when recording a RECEIPT movement.
   *
   * @param id the product to update
   * @param quantity units to add (must be &gt;= 1)
   */
  void addStock(Long id, Integer quantity);
}
