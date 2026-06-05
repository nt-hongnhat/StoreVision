/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 16:06:32
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.ResponseCode;
import com.nthn.storevision.constant.api.ResponseDescription;
import com.nthn.storevision.controller.OrderItemController;
import com.nthn.storevision.dto.OrderItemDTO;
import com.nthn.storevision.service.orderitem.OrderItemCreationService;
import com.nthn.storevision.service.orderitem.OrderItemDeletionService;
import com.nthn.storevision.service.orderitem.OrderItemRetrievalService;
import com.nthn.storevision.service.orderitem.OrderItemUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Version 1 implementation of {@link OrderItemController}. */
@Tag(name = "Order Item", description = "Order item management endpoints")
@Slf4j
@RestController
@RequestMapping(value = "/order-items", version = "v1")
@RequiredArgsConstructor
public class OrderItemControllerV1 implements OrderItemController {

  private final OrderItemCreationService creationService;
  private final OrderItemDeletionService deletionService;
  private final OrderItemRetrievalService retrievalService;
  private final OrderItemUpdateService updateService;

  @Operation(
      summary = "Create a new order item",
      description = "Adds a new line item to an existing order and persists it to the database.")
  @ApiResponse(responseCode = ResponseCode.CREATED, description = ResponseDescription.CREATED)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @Override
  public OrderItemDTO createOrderItem(@Valid @RequestBody final OrderItemDTO dto) {
    log.debug("POST /order-items");
    return creationService.create(dto);
  }

  @Operation(
      summary = "Soft-delete an order item by ID",
      description =
          "Marks the order item as deleted without physically removing the record."
              + " The record can be restored later.")
  @ApiResponse(responseCode = ResponseCode.NO_CONTENT, description = ResponseDescription.DELETED)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  @Override
  public void deleteOrderItem(
      @Parameter(description = ResponseDescription.ORDER_ITEM_ID) @PathVariable final Long id) {
    log.debug("DELETE /order-items/{}", id);
    deletionService.markAsDeleted(id);
  }

  @Operation(
      summary = "Retrieve all order items",
      description = "Returns a list of all active (non-deleted) order item records.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping
  @Override
  public List<OrderItemDTO> getAllOrderItems() {
    log.debug("GET /order-items");
    return retrievalService.findAllOrderItemsAsDTO();
  }

  @Operation(
      summary = "Retrieve an order item by ID",
      description = "Returns the order item record with the specified ID.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @GetMapping("/{id}")
  @Override
  public OrderItemDTO getOrderItemById(
      @Parameter(description = ResponseDescription.ORDER_ITEM_ID) @PathVariable final Long id) {
    log.debug("GET /order-items/{}", id);
    return retrievalService.findOrderItemDTOById(id);
  }

  @Operation(
      summary = "Update an order item by ID",
      description =
          "Updates the mutable fields (quantity, unitPrice) of an existing order item."
              + " Order and product associations cannot be changed via this endpoint.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PutMapping("/{id}")
  @Override
  public OrderItemDTO updateOrderItem(
      @Parameter(description = ResponseDescription.ORDER_ITEM_ID) @PathVariable final Long id,
      @Valid @RequestBody final OrderItemDTO dto) {
    log.debug("PUT /order-items/{}", id);
    return updateService.update(id, dto);
  }
}
