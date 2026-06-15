/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemController.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 11:44:10
 */

package com.nthn.storevision.controller;

import com.nthn.storevision.dto.order_item.OrderItemDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

/**
 * API contract for order-item management endpoints.
 *
 * @see com.nthn.storevision.controller.v1.OrderItemControllerV1
 */
public interface OrderItemController {

  /**
   * Creates a new order item.
   *
   * @param dto the order item data to persist
   * @return the created order item
   */
  OrderItemDTO createOrderItem(@Valid OrderItemDTO dto);

  /**
   * Soft-deletes an order item by ID.
   *
   * @param id the ID of the order item to delete
   */
  void deleteOrderItem(Long id);

  /**
   * Retrieves one page of active order items, optionally filtered by their parent order.
   *
   * @param page zero-based page index; negative values are treated as 0
   * @param size requested page size; clamped to the range [1, 100]
   * @param orderId the parent order ID to filter by; may be {@code null} to return order items for
   *     all orders
   * @return the requested page of order items
   */
  Page<OrderItemDTO> getAllOrderItems(int page, int size, Long orderId);

  /**
   * Retrieves an order item by ID.
   *
   * @param id the ID of the order item to retrieve
   * @return the matching order item
   */
  OrderItemDTO getOrderItemById(Long id);

  /**
   * Updates an existing order item.
   *
   * @param id the ID of the order item to update
   * @param dto the updated order item data
   * @return the updated order item
   */
  OrderItemDTO updateOrderItem(Long id, @Valid OrderItemDTO dto);
}
