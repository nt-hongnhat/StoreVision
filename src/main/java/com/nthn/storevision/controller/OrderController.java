/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: OrderController.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-08 17:16:26
 */

package com.nthn.storevision.controller;

import com.nthn.storevision.dto.order.OrderDTO;
import com.nthn.storevision.dto.request.OrderCreationRequest;
import com.nthn.storevision.dto.request.OrderStatusUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

/**
 * API contract for order management endpoints.
 *
 * @see com.nthn.storevision.controller.v1.OrderControllerV1
 */
@SuppressWarnings("unused")
public interface OrderController {

  /**
   * Marks a packing task as complete for the specified task ID.
   *
   * @param taskId the unique identifier of the packing task to complete
   */
  void completePacking(String taskId);

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
   * Retrieves one page of active orders, the most recent first.
   *
   * @param page zero-based page index
   * @param size page size
   * @return the requested page of orders
   */
  Page<OrderDTO> getAllOrders(int page, int size);

  /**
   * Retrieves an order by ID.
   *
   * @param id the ID of the order to retrieve
   * @return the matching order
   */
  OrderDTO getOrderById(Long id);

  /**
   * Initiates the order creation process for a specific product.
   *
   * @param productId the ID of the product for which the order is to be started
   */
  void startOrder(Long productId);

  /**
   * Updates an existing order.
   *
   * @param id the ID of the order to update
   * @param dto the updated order data
   * @return the updated order
   */
  OrderDTO updateOrder(Long id, @Valid OrderDTO dto);

  /**
   * Transitions a PENDING order to {@code COMPLETED} or {@code CANCELLED} (BR-O02); completed and
   * cancelled orders are final states.
   *
   * <p>{@code COMPLETED}: re-checks stock availability (BR-O01), deducts stock and records {@code
   * ISSUE} stock movements (BR-I05), awards loyalty points and increments the voucher usage count.
   *
   * <p>{@code CANCELLED}: no stock is deducted and no loyalty points are awarded.
   *
   * @param id the ID of the order to transition
   * @param request the target status; must not be {@code null} and must be {@code COMPLETED} or
   *     {@code CANCELLED}
   * @return the updated order
   */
  OrderDTO updateOrderStatus(Long id, @Valid OrderStatusUpdateRequest request);
}
