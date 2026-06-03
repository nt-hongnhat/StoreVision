/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.dto.OrderItemDTO;
import com.nthn.storevision.dto.response.ApiSuccessResponse;
import com.nthn.storevision.factory.ApiSuccessResponseFactory;
import com.nthn.storevision.service.orderitem.OrderItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for managing order items. */
@Slf4j
@RestController
@RequestMapping("/api/v1/order-items")
@RequiredArgsConstructor
public class OrderItemControllerV1 {

  private final ApiSuccessResponseFactory successFactory;
  private final OrderItemService orderItemService;

  @PostMapping
  public ResponseEntity<ApiSuccessResponse<OrderItemDTO>> createOrderItem(
      @Valid @RequestBody final OrderItemDTO dto, final HttpServletRequest request) {
    log.debug("POST /api/v1/order-items");
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(wrap(request, orderItemService.create(dto)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrderItem(@PathVariable final Long id) {
    log.debug("DELETE /api/v1/order-items/{}", id);
    orderItemService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<ApiSuccessResponse<List<OrderItemDTO>>> getAllOrderItems(
      final HttpServletRequest request) {
    log.debug("GET /api/v1/order-items");
    return ResponseEntity.ok(wrap(request, orderItemService.findAll()));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<OrderItemDTO>> getOrderItemById(
      @PathVariable final Long id, final HttpServletRequest request) {
    log.debug("GET /api/v1/order-items/{}", id);
    return ResponseEntity.ok(wrap(request, orderItemService.findById(id)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiSuccessResponse<OrderItemDTO>> updateOrderItem(
      @PathVariable final Long id,
      @Valid @RequestBody final OrderItemDTO dto,
      final HttpServletRequest request) {
    log.debug("PUT /api/v1/order-items/{}", id);
    return ResponseEntity.ok(wrap(request, orderItemService.update(id, dto)));
  }

  @SuppressWarnings("unchecked")
  private <T> ApiSuccessResponse<T> wrap(final HttpServletRequest request, final T data) {
    return (ApiSuccessResponse<T>) successFactory.create(request.getRequestURI(), data);
  }
}
