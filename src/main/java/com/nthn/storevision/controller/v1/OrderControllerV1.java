/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:18:32
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.mapper.OrderMapper;
import com.nthn.storevision.service.order.OrderCreationService;
import com.nthn.storevision.service.order.OrderDeletionService;
import com.nthn.storevision.service.order.OrderRetrievalService;
import com.nthn.storevision.service.order.OrderUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing orders.
 * Provides endpoints for CRUD operations on orders.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderControllerV1 {

  private final OrderCreationService orderCreationService;
  private final OrderRetrievalService orderRetrievalService;
  private final OrderUpdateService orderUpdateService;
  private final OrderDeletionService orderDeletionService;
  private final OrderMapper orderMapper;

  /**
   * Retrieves a list of all orders.
   *
   * @return A ResponseEntity containing a list of OrderDTOs.
   */
  @GetMapping
  public ResponseEntity<List<OrderDTO>> getAllOrders() {
    return ResponseEntity.ok(orderRetrievalService.findAllOrdersAsDTO());
  }

  /**
   * Retrieves an order by its unique identifier.
   *
   * @param id The ID of the order to retrieve.
   * @return A ResponseEntity containing the OrderDTO if found, or a not found status.
   */
  @GetMapping("/{id}")
  public ResponseEntity<OrderDTO> getOrderById(@PathVariable final Long id) {
    return ResponseEntity.ok(orderRetrievalService.findOrderDTOById(id));
  }

  /**
   * Creates a new order.
   *
   * @param orderDTO The OrderDTO containing the details of the order to create.
   * @return A ResponseEntity containing the created OrderDTO.
   */
  @PostMapping
  public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody final OrderDTO orderDTO) {
    OrderEntity orderEntity = orderMapper.toEntity(orderDTO);
    orderCreationService.create(orderEntity);
    return new ResponseEntity<>(orderMapper.toDTO(orderEntity), HttpStatus.CREATED);
  }

  /**
   * Updates an existing order.
   *
   * @param id The ID of the order to update.
   * @param orderDTO The OrderDTO containing the updated details.
   * @return A ResponseEntity containing the updated OrderDTO.
   */
  @PutMapping("/{id}")
  public ResponseEntity<OrderDTO> updateOrder(@PathVariable final Long id, @Valid @RequestBody final OrderDTO orderDTO) {
    OrderEntity existingOrder = orderMapper.toEntity(orderRetrievalService.findOrderDTOById(id));
    existingOrder.setOrderDate(orderDTO.getOrderDate());
    existingOrder.setTotalAmount(orderDTO.getTotalAmount());
    existingOrder.setStatus(orderDTO.getStatus());
    // Note: Customer cannot be updated directly via DTO in this simplified setup.
    // If customer update is needed, it should be handled separately or with a more complex DTO/logic.
    orderUpdateService.update(existingOrder);
    return ResponseEntity.ok(orderMapper.toDTO(existingOrder));
  }

  /**
   * Deletes an order by its unique identifier.
   *
   * @param id The ID of the order to delete.
   * @return A ResponseEntity with no content if deletion is successful.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable final Long id) {
    orderDeletionService.permanentlyDelete(id);
    return ResponseEntity.noContent().build();
  }
}
