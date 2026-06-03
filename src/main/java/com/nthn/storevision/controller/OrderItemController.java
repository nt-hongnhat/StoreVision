package com.nthn.storevision.controller;

import com.nthn.storevision.dto.OrderItemDTO;
import jakarta.validation.Valid;
import java.util.List;

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
   * Retrieves all active order items.
   *
   * @return list of all non-deleted order items
   */
  List<OrderItemDTO> getAllOrderItems();

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
   * @param id  the ID of the order item to update
   * @param dto the updated order item data
   * @return the updated order item
   */
  OrderItemDTO updateOrderItem(Long id, @Valid OrderItemDTO dto);
}
