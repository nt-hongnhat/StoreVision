/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: Product.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 21:43:46
 */

package com.nthn.storevision.model;

import java.math.BigDecimal;
import java.util.Map;

/** Represents a product in the StoreVision domain. */
public interface Product extends CustomAuditable<Long> {

  /**
   * Retrieves a map of attributes associated with the product.
   *
   * @return a map where the keys and values are both strings, representing the attributes of the
   *     product. The map may be empty but will not be null.
   */
  Map<String, String> getAttributes();

  /**
   * Sets the attributes of the product.
   *
   * @param attributes a map where the keys and values are both strings, representing the attributes
   *     of the product. The map should not be null but may be empty.
   * @return the updated {@code Product} instance.
   */
  Product setAttributes(Map<String, String> attributes);

  /**
   * Gets the category of the product.
   *
   * @return the category as a {@link String}, or {@code null} if not assigned.
   */
  String getCategory();

  /**
   * Sets the category of the product.
   *
   * @param category the category to set.
   * @return the updated {@code Product} instance.
   */
  Product setCategory(String category);

  /**
   * Retrieves the description of the product.
   *
   * @return the description as a {@link String}, or {@code null} if no description is available.
   */
  String getDescription();

  Product setDescription(String description);

  /**
   * Gets the name of the product.
   *
   * @return the name as a {@link String}.
   */
  String getName();

  /**
   * Sets the name of the product.
   *
   * @param name the name to set.
   * @return the updated {@code Product} instance.
   */
  Product setName(String name);

  /**
   * Gets the unit price of the product.
   *
   * @return the price as a {@link BigDecimal}.
   */
  BigDecimal getPrice();

  /**
   * Sets the unit price of the product.
   *
   * @param price the price to set.
   * @return the updated {@code Product} instance.
   */
  Product setPrice(BigDecimal price);

  /**
   * Retrieves the SKU (Stock Keeping Unit) of the product.
   *
   * @return the SKU as a {@link String}, or {@code null} if not assigned.
   */
  String getSku();

  /**
   * Sets the SKU (Stock Keeping Unit) of the product.
   *
   * @param sku the SKU to set. Must be a non-null {@link String} that uniquely identifies the
   *     product.
   * @return the updated {@code Product} instance.
   */
  Product setSku(String sku);

  /**
   * Gets the current stock quantity of the product.
   *
   * @return the stock quantity as an {@link Integer}.
   */
  Integer getStockQuantity();

  /**
   * Sets the stock quantity of the product.
   *
   * @param stockQuantity the stock quantity to set.
   * @return the updated {@code Product} instance.
   */
  Product setStockQuantity(Integer stockQuantity);
}
