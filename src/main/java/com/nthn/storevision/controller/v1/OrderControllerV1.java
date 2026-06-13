/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:28:20
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.ResponseCode;
import com.nthn.storevision.constant.api.ResponseDescription;
import com.nthn.storevision.controller.OrderController;
import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.dto.request.OrderCreationRequest;
import com.nthn.storevision.dto.response.PageDTO;
import com.nthn.storevision.service.customer.CustomerRetrievalService;
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
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value = "/orders", version = "v1")
@RequiredArgsConstructor
public class OrderControllerV1 implements OrderController {

  private static final String CANCEL_ORDER_DESC =
      "Cancels a PENDING order (PENDING → CANCELLED). No stock is deducted and no loyalty points"
          + " are awarded. A COMPLETED order cannot be cancelled (BR-O02). CANCELLED is a final"
          + " state.";
  private static final String COMPLETE_ORDER_DESC =
      "Confirms payment for a PENDING order (PENDING → COMPLETED). Re-checks stock availability"
          + " (BR-O01), deducts stock and records ISSUE stock movements in the same transaction"
          + " (BR-I05), awards loyalty points to the customer and increments the voucher usage"
          + " count. COMPLETED is a final state.";
  private static final String COMPLETE_STOCK_DESC =
      "Stock quantity is insufficient for one or more order items at completion time (BR-O01)";
  private static final String CREATE_ORDER_DESC =
      "Creates a sales order in PENDING status. Validates the customer, the products and the stock"
          + " availability without deducting it (BR-O01), validates the optional voucher"
          + " (BR-V01–BR-V04), computes subtotal, discount, tax and total server-side (BR-O03) and"
          + " locks each item's unit price at creation time (BR-O04)."
          + " The order must contain at least one item (BR-O05).";
  private static final String INVALID_STATE_DESC =
      "Order is not in PENDING status — completed and cancelled orders are final (BR-O02)";
  private static final String UNPROCESSABLE_DESC =
      "Stock quantity is insufficient (BR-O01) or the voucher cannot be applied (BR-V01–BR-V04)";
  private final OrderCreationService creationService;
  private final CustomerRetrievalService customerRetrievalService;
  private final OrderDeletionService deletionService;
  private final OrderRetrievalService retrievalService;
  private final OrderUpdateService updateService;

  /** {@inheritDoc} */
  @Operation(summary = "Cancel a pending order", description = CANCEL_ORDER_DESC)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ApiResponse(responseCode = ResponseCode.CONFLICT, description = INVALID_STATE_DESC)
  @PatchMapping("/{id}/cancel")
  @Override
  public OrderDTO cancelOrder(
      @Parameter(description = ResponseDescription.ORDER_ID) @PathVariable final Long id) {
    log.debug("PATCH /orders/{}/cancel", id);
    return updateService.cancel(id);
  }

  /** {@inheritDoc} */
  @Operation(summary = "Confirm payment for a pending order", description = COMPLETE_ORDER_DESC)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ApiResponse(responseCode = ResponseCode.CONFLICT, description = INVALID_STATE_DESC)
  @ApiResponse(
      responseCode = ResponseCode.UNPROCESSABLE_ENTITY,
      description = COMPLETE_STOCK_DESC)
  @PatchMapping("/{id}/complete")
  @Override
  public OrderDTO completeOrder(
      @Parameter(description = ResponseDescription.ORDER_ID) @PathVariable final Long id) {
    log.debug("PATCH /orders/{}/complete", id);
    return updateService.complete(id);
  }

  /** {@inheritDoc} */
  @Operation(summary = "Create a new sales order", description = CREATE_ORDER_DESC)
  @ApiResponse(responseCode = ResponseCode.CREATED, description = ResponseDescription.CREATED)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ApiResponse(responseCode = ResponseCode.UNPROCESSABLE_ENTITY, description = UNPROCESSABLE_DESC)
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
      @Parameter(description = ResponseDescription.PAGE_SIZE)
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

  @Operation(
      summary = "Retrieve a customer's order history",
      description = "Returns all active orders of the customer, most recent first.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @GetMapping("/customer/{customerId}")
  public List<OrderDTO> getCustomerOrdersByCustomerId(
      @Parameter(description = ResponseDescription.CUSTOMER_ID) @PathVariable
          final Long customerId) {
    log.debug("GET /orders/customer/{}", customerId);
    return customerRetrievalService.findOrderHistory(customerId);
  }
}
