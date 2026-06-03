package com.nthn.storevision.controller;

import com.nthn.storevision.dto.OrderDTO;
import jakarta.validation.Valid;
import java.util.List;

/**
 * API contract for order management endpoints.
 *
 * @see com.nthn.storevision.controller.v1.OrderControllerV1
 */
public interface OrderController {

  /**
   * Creates a new order.
   *
   * @param dto the order data to persist
   * @return the created order
   */
  OrderDTO createOrder(@Valid OrderDTO dto);

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
   * @param id  the ID of the order to update
   * @param dto the updated order data
   * @return the updated order
   */
  OrderDTO updateOrder(Long id, @Valid OrderDTO dto);
}
