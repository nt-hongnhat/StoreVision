/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 15:47:14
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.*;
import com.nthn.storevision.controller.InventoryController;
import com.nthn.storevision.dto.inventory.InventoryDTO;
import com.nthn.storevision.dto.stock_movement.StockMovementDTO;
import com.nthn.storevision.dto.stock_movement.StockReceiptDTO;
import com.nthn.storevision.dto.request.InventoryAdjustmentRequest;
import com.nthn.storevision.dto.request.StockReceiptRequest;
import com.nthn.storevision.service.inventory.InventoryAdjustmentService;
import com.nthn.storevision.service.inventory.InventoryCreationService;
import com.nthn.storevision.service.inventory.InventoryRetrievalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Version 1 implementation of {@link InventoryController}. */
@Tag(name = "Inventory", description = "Inventory management endpoints")
@Slf4j
@RestController
@RequestMapping(value = EndpointDefinitions.INVENTORY, version = "v1")
@RequiredArgsConstructor
public class InventoryControllerV1 implements InventoryController {

  private final InventoryAdjustmentService adjustmentService;
  private final InventoryCreationService creationService;
  private final InventoryRetrievalService retrievalService;

  /** {@inheritDoc} */
  @Operation(
      summary = "Adjust stock quantity for a product",
      description = OperationDescription.INVENTORY_ADJUSTMENT)
  @ApiResponse(
      responseCode = ResponseCode.OK,
      description = ResponseDescription.INVENTORY_ADJUSTMENT_RECORDED)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
  @PostMapping("/adjustments")
  @Override
  public InventoryDTO adjustStock(@Valid @RequestBody final InventoryAdjustmentRequest request) {
    log.debug(
        "POST /inventory/adjustments — productId: {}, quantity: {}",
        request.productId(),
        request.quantity());
    return adjustmentService.adjustStock(request);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Record a stock receipt",
      description = OperationDescription.INVENTORY_RECEIPT)
  @ApiResponse(
      responseCode = ResponseCode.CREATED,
      description = ResponseDescription.INVENTORY_RECEIPT_CREATED)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(EndpointDefinitions.RECEIPTS)
  @Override
  public StockReceiptDTO createReceipt(@Valid @RequestBody final StockReceiptRequest request) {
    log.debug("POST /inventory/receipts — productId: {}", request.getProductId());
    return creationService.receiveStock(request);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "List product stock levels",
      description = OperationDescription.INVENTORY_LIST)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
  @GetMapping
  @Override
  public List<InventoryDTO> getAllInventory(
      @Parameter(description = ResponseDescription.INVENTORY_LOW_STOCK)
          @RequestParam(required = false, defaultValue = "false")
          final boolean lowStock) {
    log.debug("GET /inventory — lowStock: {}", lowStock);
    return lowStock ? retrievalService.findLowStockProducts() : retrievalService.findAllInventory();
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Get stock level for a single product",
      description = OperationDescription.INVENTORY_GET_BY_PRODUCT_ID)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER','STAFF')")
  @GetMapping("/{productId}")
  @Override
  public InventoryDTO getInventoryByProductId(
      @Parameter(description = ParameterDescription.PRODUCT_ID) @PathVariable
          final Long productId) {
    log.debug("GET /inventory/{}", productId);
    return retrievalService.findInventoryByProductId(productId);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "List stock movements for a product",
      description = OperationDescription.INVENTORY_MOVEMENT_HISTORY)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
  @GetMapping("/{productId}/movements")
  @Override
  public List<StockMovementDTO> getMovementsByProductId(
      @Parameter(description = ParameterDescription.PRODUCT_ID) @PathVariable
          final Long productId) {
    log.debug("GET /inventory/{}/movements", productId);
    return retrievalService.findMovementsByProductId(productId);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "List stock receipt history (paginated)",
      description = OperationDescription.INVENTORY_RECEIPT_HISTORY)
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
  @GetMapping(EndpointDefinitions.RECEIPTS)
  @Override
  public Page<StockReceiptDTO> getReceiptHistory(
      @Parameter(description = ResponseDescription.INVENTORY_RECEIPT_FROM_DATE)
          @RequestParam(required = false)
          final LocalDateTime from,
      @Parameter(description = ResponseDescription.INVENTORY_RECEIPT_TO_DATE)
          @RequestParam(required = false)
          final LocalDateTime to,
      @Parameter(description = ParameterDescription.PAGE_INDEX)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_PAGE)
          final int page,
      @Parameter(description = ParameterDescription.PAGE_SIZE)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_SIZE)
          final int size) {
    log.debug(
        "GET /inventory/receipts — from: {}, to: {}, page: {}, size: {}", from, to, page, size);
    return retrievalService.findReceiptHistory(from, to, page, size);
  }
}
