/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: OrderUpdateService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:40:22
 */

package com.nthn.storevision.service.order;

import com.nthn.storevision.dto.order.OrderDTO;
import com.nthn.storevision.exception.order.InvalidOrderStateException;
import com.nthn.storevision.service.generic.Updatable;
import org.jspecify.annotations.NonNull;

/**
 * Service interface for updating orders, including the state transitions of the order lifecycle:
 * {@code PENDING → COMPLETED} (payment confirmed) and {@code PENDING → CANCELLED}.
 *
 * @see Updatable
 */
public interface OrderUpdateService extends Updatable<OrderDTO, Long> {

  /**
   * Cancels a {@code PENDING} order ({@code PENDING → CANCELLED}). No stock is deducted and no
   * loyalty points are awarded. A {@code COMPLETED} order cannot be cancelled (BR-O02).
   *
   * @param orderId the order to cancel; must not be {@code null}
   * @return the cancelled order as a {@link OrderDTO}; never {@code null}
   * @throws com.nthn.storevision.exception.EntityNotFoundException if the order does not exist
   * @throws InvalidOrderStateException if the order is not in
   *     {@code PENDING} status (BR-O02)
   */
  OrderDTO cancel(@NonNull Long orderId);

  /**
   * Confirms payment for a {@code PENDING} order ({@code PENDING → COMPLETED}). Re-checks stock
   * availability for every item to avoid race conditions (BR-O01), deducts the stock, records an
   * {@code ISSUE} stock movement per item in the same transaction (BR-I05), awards loyalty points
   * to the customer and increments the applied voucher's usage count.
   *
   * @param orderId the order to complete; must not be {@code null}
   * @return the completed order as a {@link OrderDTO}; never {@code null}
   * @throws com.nthn.storevision.exception.EntityNotFoundException if the order does not exist
   * @throws InvalidOrderStateException if the order is not in
   *     {@code PENDING} status
   * @throws com.nthn.storevision.exception.InsufficientStockException if any item's stock is
   *     insufficient at completion time (BR-O01)
   */
  OrderDTO complete(@NonNull Long orderId);
}
