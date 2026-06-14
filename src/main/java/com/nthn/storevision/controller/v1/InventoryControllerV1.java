/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 17:42:34
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.ResponseCode;
import com.nthn.storevision.constant.api.ResponseDescription;
import com.nthn.storevision.controller.InventoryController;
import com.nthn.storevision.dto.InventoryDTO;
import com.nthn.storevision.dto.StockMovementDTO;
import com.nthn.storevision.dto.StockReceiptDTO;
import com.nthn.storevision.dto.request.InventoryAdjustmentRequest;
import com.nthn.storevision.dto.request.StockReceiptRequest;
import com.nthn.storevision.dto.response.PageDTO;
import com.nthn.storevision.service.inventory.InventoryAdjustmentService;
import com.nthn.storevision.service.inventory.InventoryCreationService;
import com.nthn.storevision.service.inventory.InventoryRetrievalService;
import com.nthn.storevision.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(value = "/inventory", version = "v1")
@RequiredArgsConstructor
public class InventoryControllerV1 implements InventoryController {

  private static final String ADJUSTMENT_RECORDED = "Stock adjustment recorded successfully";
  private static final String LOW_STOCK_DESC =
      "When true, returns only products with stock quantity at or below the configured low-stock"
          + " threshold (default 10, BR-I04)";
  private static final String MOVEMENT_HISTORY_DESC =
      "All stock movements (RECEIPT, ISSUE, ADJUSTMENT) for the product, ordered by most recent first.";
  private static final String RECEIPT_CREATED = "Stock receipt recorded successfully";
  private static final String RECEIPT_HISTORY_DESC =
      "RECEIPT movements ordered by most recent first. Both date parameters are optional: omit to retrieve full history.";
  private final InventoryAdjustmentService adjustmentService;
  private final InventoryCreationService creationService;
  private final InventoryRetrievalService retrievalService;

  /** {@inheritDoc} */
  @Operation(
      summary = "Adjust stock quantity for a product",
      description =
          "Records an ADJUSTMENT stock movement and updates the product's stock quantity."
              + " Quantity can be positive (increase) or negative (decrease).")
  @ApiResponse(responseCode = ResponseCode.OK, description = ADJUSTMENT_RECORDED)
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
      description =
          "Creates a RECEIPT stock movement and increments the product's stock quantity."
              + " Enforces BR-I01 (performed_by) and BR-I02 (quantity >= 1).")
  @ApiResponse(responseCode = ResponseCode.CREATED, description = RECEIPT_CREATED)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/receipts")
  @Override
  public StockReceiptDTO createReceipt(@Valid @RequestBody final StockReceiptRequest request) {
    log.debug("POST /inventory/receipts — productId: {}", request.getProductId());
    return creationService.receiveStock(request);
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "List product stock levels",
      description =
          "Returns current stock quantity and price for active products. When lowStock=true,"
              + " only products at or below the configured low-stock threshold are returned"
              + " (BR-I04).")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
  @GetMapping
  @Override
  public List<InventoryDTO> getAllInventory(
      @Parameter(description = LOW_STOCK_DESC) @RequestParam(required = false, defaultValue = "false")
          final boolean lowStock) {
    log.debug("GET /inventory — lowStock: {}", lowStock);
    return lowStock ? retrievalService.findLowStockProducts() : retrievalService.findAllInventory();
  }

  /** {@inheritDoc} */
  @Operation(
      summary = "Get stock level for a single product",
      description = "Returns current stock details for the specified product.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER','STAFF')")
  @GetMapping("/{productId}")
  @Override
  public InventoryDTO getInventoryByProductId(
      @Parameter(description = ResponseDescription.PRODUCT_ID) @PathVariable final Long productId) {
    log.debug("GET /inventory/{}", productId);
    return retrievalService.findInventoryByProductId(productId);
  }

  /** {@inheritDoc} */
  @Operation(summary = "List stock movements for a product", description = MOVEMENT_HISTORY_DESC)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
  @GetMapping("/{productId}/movements")
  @Override
  public List<StockMovementDTO> getMovementsByProductId(
      @Parameter(description = ResponseDescription.PRODUCT_ID) @PathVariable final Long productId) {
    log.debug("GET /inventory/{}/movements", productId);
    return retrievalService.findMovementsByProductId(productId);
  }

  /** {@inheritDoc} */
  @Operation(summary = "List stock receipt history (paginated)", description = RECEIPT_HISTORY_DESC)
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
  @GetMapping("/receipts")
  @Override
  public PageDTO<StockReceiptDTO> getReceiptHistory(
      @Parameter(description = "Start of date range (ISO-8601 datetime), inclusive")
          @RequestParam(required = false)
          final LocalDateTime from,
      @Parameter(description = "End of date range (ISO-8601 datetime), inclusive")
          @RequestParam(required = false)
          final LocalDateTime to,
      @Parameter(description = ResponseDescription.PAGE_INDEX)
          @RequestParam(defaultValue = PaginationUtil.DEFAULT_PAGE)
          final int page,
      @Parameter(description = ResponseDescription.PAGE_SIZE)
          @RequestParam(defaultValue = PaginationUtil.DEFAULT_SIZE)
          final int size) {
    log.debug(
        "GET /inventory/receipts — from: {}, to: {}, page: {}, size: {}", from, to, page, size);
    return retrievalService.findReceiptHistory(from, to, page, size);
  }
}
