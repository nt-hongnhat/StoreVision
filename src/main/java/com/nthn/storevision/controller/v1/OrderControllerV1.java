/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 12:27:40
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.ApiPathConstants;
import com.nthn.storevision.constant.OrderStatus;
import com.nthn.storevision.constant.api.OperationDescription;
import com.nthn.storevision.constant.api.ResponseCode;
import com.nthn.storevision.constant.api.ResponseDescription;
import com.nthn.storevision.controller.OrderController;
import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.dto.request.OrderCreationRequest;
import com.nthn.storevision.dto.request.OrderStatusUpdateRequest;
import com.nthn.storevision.dto.response.PageDTO;
import com.nthn.storevision.exception.InvalidOrderStatusTransitionException;
import com.nthn.storevision.service.order.OrderCreationService;
import com.nthn.storevision.service.order.OrderDeletionService;
import com.nthn.storevision.service.order.OrderRetrievalService;
import com.nthn.storevision.service.order.OrderUpdateService;
import com.nthn.storevision.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Version 1 implementation of {@link OrderController}. */
@Tag(name = "Order", description = "Order management endpoints")
@Slf4j
@RestController
@RequestMapping(value = ApiPathConstants.Constants.ORDERS, version = "v1")
@RequiredArgsConstructor
public class OrderControllerV1 implements OrderController {

  private final OrderCreationService creationService;
  private final OrderDeletionService deletionService;
  private final OrderRetrievalService retrievalService;
  private final OrderUpdateService updateService;

  /** {@inheritDoc} */
  @Operation(
      summary = "Create a new sales order",
      description = OperationDescription.CREATE_ORDER)
  @ApiResponse(responseCode = ResponseCode.CREATED, description = ResponseDescription.CREATED)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ApiResponse(
      responseCode = ResponseCode.UNPROCESSABLE_ENTITY,
      description = ResponseDescription.UNPROCESSABLE)
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @Override
  public OrderDTO createOrder(@Valid @RequestBody final OrderCreationRequest request) {
    log.debug("POST /orders — customerId: {}", request.getCustomerId());
    return creationService.createOrder(request);
  }

  @Operation(
      summary = "Soft-delete an order by ID",
      description =
          "Marks the order as deleted without physically removing the record."
              + " The record can be restored later.")
  @ApiResponse(responseCode = ResponseCode.NO_CONTENT, description = ResponseDescription.DELETED)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  @Override
  public void deleteOrder(
      @Parameter(description = ResponseDescription.ORDER_ID) @PathVariable final Long id) {
    log.debug("DELETE /orders/{}", id);
    deletionService.markAsDeleted(id);
  }

  @Operation(
      summary = "Retrieve orders (paginated)",
      description = "Returns one page of active (non-deleted) order records, most recent first.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping
  @Override
  public PageDTO<OrderDTO> getAllOrders(
      @Parameter(description = ResponseDescription.PAGE_INDEX)
          @RequestParam(defaultValue = PaginationUtil.DEFAULT_PAGE)
          final int page,
      @Parameter(description = PaginationUtil.DEFAULT_SIZE)
          @RequestParam(defaultValue = PaginationUtil.DEFAULT_SIZE)
          final int size) {
    log.debug("GET /orders — page: {}, size: {}", page, size);
    return retrievalService.findOrdersPage(page, size);
  }

  @Operation(
      summary = "Retrieve an order by ID",
      description = "Returns the order record with the specified ID.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @GetMapping("/{id}")
  @Override
  public OrderDTO getOrderById(
      @Parameter(description = ResponseDescription.ORDER_ID) @PathVariable final Long id) {
    log.debug("GET /orders/{}", id);
    return retrievalService.findOrderDTOById(id);
  }

  @Operation(
      summary = "Update an order by ID",
      description =
          "Updates the mutable fields (orderDate, totalAmount, status) of an existing order."
              + " Customer association cannot be changed via this endpoint.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PutMapping("/{id}")
  @Override
  public OrderDTO updateOrder(
      @Parameter(description = ResponseDescription.ORDER_ID) @PathVariable final Long id,
      @Valid @RequestBody final OrderDTO dto) {
    log.debug("PUT /orders/{}", id);
    return updateService.update(id, dto);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Transition the status of a pending order",
      description = OperationDescription.UPDATE_STATUS)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ApiResponse(
      responseCode = ResponseCode.CONFLICT,
      description = ResponseDescription.INVALID_STATE)
  @ApiResponse(
      responseCode = ResponseCode.UNPROCESSABLE_ENTITY,
      description = ResponseDescription.COMPLETE_STOCK)
  @PreAuthorize(
      "hasAnyRole('ADMIN','MANAGER')"
          + " or (#request.status == T(com.nthn.storevision.constant.OrderStatus).COMPLETED"
          + " and hasRole('STAFF'))")
  @PatchMapping("/{id}")
  @Override
  public OrderDTO updateOrderStatus(
      @Parameter(description = ResponseDescription.ORDER_ID) @PathVariable final Long id,
      @Valid @RequestBody final OrderStatusUpdateRequest request) {
    log.debug("PATCH /orders/{} — status: {}", id, request.getStatus());
    return switch (request.getStatus()) {
      case COMPLETED -> updateService.complete(id);
      case CANCELLED -> updateService.cancel(id);
      case PENDING -> throw new InvalidOrderStatusTransitionException(OrderStatus.PENDING);
    };
  }
}
