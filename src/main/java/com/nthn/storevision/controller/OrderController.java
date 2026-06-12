/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderController.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 21:06:36
 */

package com.nthn.storevision.controller;

import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.dto.request.OrderCreationRequest;
import jakarta.validation.Valid;
import java.util.List;

/**
 * API contract for order management endpoints.
 *
 * @see com.nthn.storevision.controller.v1.OrderControllerV1
 */
public interface OrderController {

  /**
   * Creates a new sales order in PENDING status. Stock is only verified, not deducted (BR-O01); the
   * total amount is computed server-side (BR-O03) and each item's unit price is locked at creation
   * time (BR-O04).
   *
   * @param request the order details: customer and at least one item (BR-O05)
   * @return the created order including its order items
   */
  OrderDTO createOrder(@Valid OrderCreationRequest request);

  /**
   * Soft-deletes an order by ID.
   *
   * @param id the ID of the order to delete
   */
  void deleteOrder(Long id);

  /**
   * Retrieves all active orders.
   *
   * @return list of all non-deleted orders
   */
  List<OrderDTO> getAllOrders();

  /**
   * Retrieves an order by ID.
   *
   * @param id the ID of the order to retrieve
   * @return the matching order
   */
  OrderDTO getOrderById(Long id);

  /**
   * Updates an existing order.
   *
   * @param id the ID of the order to update
   * @param dto the updated order data
   * @return the updated order
   */
  OrderDTO updateOrder(Long id, @Valid OrderDTO dto);
}
