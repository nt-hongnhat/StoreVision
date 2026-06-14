/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemRetrievalService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 12:04:55
 */

package com.nthn.storevision.service.orderitem;

import com.nthn.storevision.dto.OrderItemDTO;
import com.nthn.storevision.dto.response.PageDTO;
import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.service.generic.Readable;

/**
 * Service interface for retrieving {@link OrderItemEntity} entities.
 *
 * <p>Extends the {@link Readable} interface to provide common read operations for {@link
 * OrderItemEntity} entities.
 */
public interface OrderItemRetrievalService extends Readable<OrderItemEntity, Long> {

  /**
   * Retrieves an order item by its identifier and maps it to {@link OrderItemDTO}.
   *
   * @param id the identifier of the order item to be retrieved.
   * @return the {@link OrderItemDTO} with the specified identifier, or null if not found.
   */
  OrderItemDTO findOrderItemDTOById(Long id);

  /**
   * Retrieves one page of active order items, optionally filtered by their parent order.
   *
   * @param page zero-based page index; negative values are treated as 0
   * @param size requested page size; clamped to the range [1, 100]
   * @param orderId the parent order ID to filter by; may be {@code null} to return order items
   *     for all orders
   * @return the requested page of {@link OrderItemDTO}s; never {@code null}
   */
  PageDTO<OrderItemDTO> findOrderItemsPage(int page, int size, Long orderId);
}
