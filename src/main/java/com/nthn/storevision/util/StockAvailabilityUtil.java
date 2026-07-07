/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: StockAvailabilityUtil.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-07-07 11:58:46
 */

package com.nthn.storevision.util;

import com.nthn.storevision.dto.request.OrderItemCreationRequest;
import com.nthn.storevision.exception.InsufficientStockException;
import com.nthn.storevision.model.Product;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;

/**
 * Stateless helper that validates whether every product referenced by an order-creation request has
 * enough stock to satisfy the aggregated requested quantity (BR-O01), summing quantities across
 * duplicate product lines.
 */
@Slf4j
@UtilityClass
public class StockAvailabilityUtil {

  /**
   * Verifies stock sufficiency for the aggregated quantities of the given order items.
   *
   * @param items the requested order items; must not be {@code null}
   * @param products the resolved products keyed by product id; must not be {@code null} and must
   *     contain an entry for every product referenced by {@code items}
   * @throws InsufficientStockException if any product's stock is below its aggregated requested
   *     quantity
   */
  public void verify(
      final @NonNull List<? extends OrderItemCreationRequest> items,
      final @NonNull Map<Long, ? extends Product> products) {
    log.debug(
        "Verifying stock availability for {} order items mapped to {} products",
        items.size(),
        products.size());
    final Map<Long, Integer> requestedPerProduct = new LinkedHashMap<>();
    items.forEach(
        item -> requestedPerProduct.merge(item.getProductId(), item.getQuantity(), Integer::sum));

    try {
      requestedPerProduct.forEach(
          (productId, requested) ->
              verifyProductStock(productId, products.get(productId), requested));
      log.debug("Stock availability verification completed successfully");
    } catch (final RuntimeException exception) {
      log.error("Stock availability verification failed due to an unexpected error", exception);
      throw exception;
    }
  }

  /**
   * Ensures a single product has enough stock for the aggregated requested quantity.
   *
   * @param productId the product identifier, used only for logging; must not be {@code null}
   * @param product the resolved product to check; must not be {@code null}
   * @param requested the aggregated requested quantity for the product
   * @throws InsufficientStockException if the product's stock is below {@code requested}
   */
  private void verifyProductStock(
      final @NonNull Long productId, final @NonNull Product product, final int requested) {
    if (product.getStockQuantity() >= requested) {
      return;
    }
    log.warn(
        "Detected insufficient stock while validating order creation request for product id={}",
        productId);
    throw new InsufficientStockException(product.getName(), product.getStockQuantity(), requested);
  }
}
