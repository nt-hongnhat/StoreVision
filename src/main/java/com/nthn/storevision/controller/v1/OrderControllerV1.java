/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: OrderControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-09-08 17:17:08
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.*;
import com.nthn.storevision.controller.OrderController;
import com.nthn.storevision.dto.order.OrderDTO;
import com.nthn.storevision.dto.request.OrderCreationRequest;
import com.nthn.storevision.dto.request.OrderStatusUpdateRequest;
import com.nthn.storevision.service.order.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
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
@RequestMapping(value = EndpointDefinitions.ORDERS, version = "v1")
@RequiredArgsConstructor
public class OrderControllerV1 implements OrderController {

  private final OrderCreationService creationService;
  private final OrderDeletionService deletionService;
  private final OrderProcessService orderProcessService;
  private final OrderRetrievalService retrievalService;
  private final OrderUpdateService updateService;

  /** {@inheritDoc} */
  @Operation(
      summary = "Complete packing task",
      description = "Completes the packing task for the specified order")
  @ApiResponse(responseCode = ResponseCode.OK, description = "Packing task completed successfully")
  @ApiResponse(responseCode = ResponseCode.UNAUTHORIZED, description = "Unauthorized access")
  @ApiResponse(responseCode = ResponseCode.FORBIDDEN, description = "Forbidden access")
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = "Order not found")
  @Override
  @PreAuthorize("hasRole('USER')")
  @PostMapping("/warehouse/tasks/{taskId}")
  @ResponseStatus(HttpStatus.OK)
  public void completePacking(@PathVariable final String taskId) {
    log.info("Complete packing task {}", taskId);
    orderProcessService.completePacking(taskId);
    log.info("Complete packing task {} done", taskId);
  }

  /** {@inheritDoc} */
  @Operation(summary = "Create a new sales order", description = OperationDescription.CREATE_ORDER)
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
    log.debug(
        "Received request to create a new order with {} order items and voucher provided={}",
        request.getItems().size(),
        null != request.getVoucherCode() && !request.getVoucherCode().isBlank());
    try {
      final OrderDTO createdOrder = creationService.createOrder(request);
      log.debug("Order creation request completed successfully and a new order was persisted");
      return createdOrder;
    } catch (final RuntimeException exception) {
      log.error("Order creation request failed due to an unexpected error", exception);
      throw exception;
    }
  }

  @Operation(
      summary = "Soft-delete an order by ID",
      description = OperationDescription.ORDER_DELETION)
  @ApiResponse(responseCode = ResponseCode.NO_CONTENT, description = ResponseDescription.DELETED)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  @Override
  public void deleteOrder(
      @Parameter(description = ParameterDescription.ORDER_ID) @PathVariable final Long id) {
    log.debug("Received request to soft-delete an existing order");
    try {
      deletionService.markAsDeleted(id);
      log.debug("Order soft-delete request completed successfully");
    } catch (final RuntimeException exception) {
      log.error("Order soft-delete request failed due to an unexpected error", exception);
      throw exception;
    }
  }

  @Operation(summary = "Retrieve orders (paginated)", description = OperationDescription.ORDER_LIST)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping
  @Override
  public Page<OrderDTO> getAllOrders(
      @Parameter(description = ParameterDescription.PAGE_INDEX)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_PAGE)
          final int page,
      @Parameter(description = ParameterDescription.PAGE_SIZE)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_SIZE)
          final int size) {
    log.debug("Received request to retrieve a paginated order list: page={}, size={}", page, size);
    if (0 > page || 0 >= size) {
      log.warn(
          "Pagination request contains unusual values that may be rejected downstream: page={}, size={}",
          page,
          size);
    }
    try {
      final Page<OrderDTO> orderPage = retrievalService.findOrdersPage(page, size);
      log.debug(
          "Paginated order retrieval completed with {} records in the current page",
          orderPage.getNumberOfElements());
      return orderPage;
    } catch (final RuntimeException exception) {
      log.error("Paginated order retrieval failed due to an unexpected error", exception);
      throw exception;
    }
  }

  @Operation(
      summary = "Retrieve an order by ID",
      description = OperationDescription.ORDER_GET_BY_ID)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @GetMapping("/{id}")
  @Override
  public OrderDTO getOrderById(
      @Parameter(description = ParameterDescription.ORDER_ID) @PathVariable final Long id) {
    log.debug("Received request to retrieve order details by identifier");
    try {
      final OrderDTO order = retrievalService.findOrderDTOById(id);
      log.debug("Order detail retrieval completed successfully");
      return order;
    } catch (final RuntimeException exception) {
      log.error("Order detail retrieval failed due to an unexpected error", exception);
      throw exception;
    }
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Start an order process",
      description = "Starts the order-processing workflow for the specified pending order")
  @ApiResponse(responseCode = ResponseCode.OK, description = "Order process started successfully")
  @ApiResponse(responseCode = ResponseCode.UNAUTHORIZED, description = "Unauthorized access")
  @ApiResponse(responseCode = ResponseCode.FORBIDDEN, description = "Forbidden access")
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = "Order not found")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @PreAuthorize("hasRole('USER')")
  @PostMapping("/start")
  @Override
  public void startOrder(
      @Parameter(description = ParameterDescription.ORDER_ID) @RequestParam final Long orderId) {
    log.debug("Received request to start order process for order id={}", orderId);
    orderProcessService.startOrderProcess(orderId);
    log.debug("Order process started successfully for order id={}", orderId);
  }

  @Operation(summary = "Update an order by ID", description = OperationDescription.ORDER_UPDATE)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PutMapping("/{id}")
  @Override
  public OrderDTO updateOrder(
      @Parameter(description = ParameterDescription.ORDER_ID) @PathVariable final Long id,
      @Valid @RequestBody final OrderDTO dto) {
    log.debug("Received request to update an existing order");
    try {
      final OrderDTO updatedOrder = updateService.update(id, dto);
      log.debug("Order update request completed successfully");
      return updatedOrder;
    } catch (final RuntimeException exception) {
      log.error("Order update request failed due to an unexpected error", exception);
      throw exception;
    }
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
          + " or (#request.status == T(com.nthn.storevision.constant.order.OrderStatus).COMPLETED"
          + " and hasRole('STAFF'))")
  @PatchMapping("/{id}")
  @Override
  public OrderDTO updateOrderStatus(
      @Parameter(description = ParameterDescription.ORDER_ID) @PathVariable final Long id,
      @Valid @RequestBody final OrderStatusUpdateRequest request) {
    log.debug(
        "Received request to transition order status to target state={}", request.getStatus());
    try {
      final OrderDTO transitionedOrder = updateService.transitionStatus(id, request.getStatus());
      log.debug(
          "Order status transition request completed successfully for target state={}",
          request.getStatus());
      return transitionedOrder;
    } catch (final RuntimeException exception) {
      log.error(
          "Order status transition request failed for target state={} due to an unexpected error",
          request.getStatus(),
          exception);
      throw exception;
    }
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Retrieve tasks assigned to the current user",
      description = "Fetches tasks assigned to the authenticated user.")
  @ApiResponse(responseCode = ResponseCode.OK, description = "Tasks assigned to the current user")
  @ApiResponse(responseCode = ResponseCode.UNAUTHORIZED, description = "Unauthorized access")
  @PreAuthorize("hasRole('USER')")
  @GetMapping("warehouse/tasks")
  public List<Map<String, Object>> getManagerTasks() {
    final List<Task> tasks = orderProcessService.getWarehouseTasks();

    return tasks.stream().map(OrderControllerV1::mapTaskToProperties).toList();
  }

  private static @NonNull Map<String, Object> mapTaskToProperties(final @NonNull TaskInfo task) {
    final Map<String, Object> map = HashMap.newHashMap(4);
    map.put("taskId", task.getId());
    map.put("taskName", task.getName());
    map.put("createTime", task.getCreateTime());
    map.put("assignee", task.getAssignee());
    return map;
  }
}
