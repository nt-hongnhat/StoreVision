/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:24:04
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.dto.OrderItemDTO;
import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.mapper.OrderItemMapper;
import com.nthn.storevision.service.orderitem.OrderItemCreationService;
import com.nthn.storevision.service.orderitem.OrderItemDeletionService;
import com.nthn.storevision.service.orderitem.OrderItemRetrievalService;
import com.nthn.storevision.service.orderitem.OrderItemUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing order items.
 * Provides endpoints for CRUD operations on order items.
 */
@RestController
@RequestMapping("/api/v1/order-items")
@RequiredArgsConstructor
public class OrderItemControllerV1 {

  private final OrderItemCreationService orderItemCreationService;
  private final OrderItemRetrievalService orderItemRetrievalService;
  private final OrderItemUpdateService orderItemUpdateService;
  private final OrderItemDeletionService orderItemDeletionService;
  private final OrderItemMapper orderItemMapper;

  /**
   * Retrieves a list of all order items.
   *
   * @return A ResponseEntity containing a list of OrderItemDTOs.
   */
  @GetMapping
  public ResponseEntity<List<OrderItemDTO>> getAllOrderItems() {
    return ResponseEntity.ok(orderItemRetrievalService.findAllOrderItemsAsDTO());
  }

  /**
   * Retrieves an order item by its unique identifier.
   *
   * @param id The ID of the order item to retrieve.
   * @return A ResponseEntity containing the OrderItemDTO if found, or a not found status.
   */
  @GetMapping("/{id}")
  public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable final Long id) {
    return ResponseEntity.ok(orderItemRetrievalService.findOrderItemDTOById(id));
  }

  /**
   * Creates a new order item.
   *
   * @param orderItemDTO The OrderItemDTO containing the details of the order item to create.
   * @return A ResponseEntity containing the created OrderItemDTO.
   */
  @PostMapping
  public ResponseEntity<OrderItemDTO> createOrderItem(@Valid @RequestBody final OrderItemDTO orderItemDTO) {
    OrderItemEntity orderItemEntity = orderItemMapper.toEntity(orderItemDTO);
    orderItemCreationService.create(orderItemEntity);
    return new ResponseEntity<>(orderItemMapper.toDTO(orderItemEntity), HttpStatus.CREATED);
  }

  /**
   * Updates an existing order item.
   *
   * @param id The ID of the order item to update.
   * @param orderItemDTO The OrderItemDTO containing the updated details.
   * @return A ResponseEntity containing the updated OrderItemDTO.
   */
  @PutMapping("/{id}")
  public ResponseEntity<OrderItemDTO> updateOrderItem(@PathVariable final Long id, @Valid @RequestBody final OrderItemDTO orderItemDTO) {
    OrderItemEntity existingOrderItem = orderItemMapper.toEntity(orderItemRetrievalService.findOrderItemDTOById(id));
    existingOrderItem.setQuantity(orderItemDTO.getQuantity());
    existingOrderItem.setUnitPrice(orderItemDTO.getUnitPrice());
    // Relationships (order, product) are typically not updated directly via DTO in this manner.
    // If needed, separate logic or DTOs would be required.
    orderItemUpdateService.update(existingOrderItem);
    return ResponseEntity.ok(orderItemMapper.toDTO(existingOrderItem));
  }

  /**
   * Deletes an order item by its unique identifier.
   *
   * @param id The ID of the order item to delete.
   * @return A ResponseEntity with no content if deletion is successful.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrderItem(@PathVariable final Long id) {
    orderItemDeletionService.permanentlyDelete(id);
    return ResponseEntity.noContent().build();
  }
}
