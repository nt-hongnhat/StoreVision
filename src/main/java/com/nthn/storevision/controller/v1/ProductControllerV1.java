/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-14 15:47:14
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.*;
import com.nthn.storevision.controller.ProductController;
import com.nthn.storevision.dto.product.ProductDTO;
import com.nthn.storevision.dto.request.ProductRestoreRequest;
import com.nthn.storevision.dto.response.PageDTO;
import com.nthn.storevision.service.product.ProductCreationService;
import com.nthn.storevision.service.product.ProductDeletionService;
import com.nthn.storevision.service.product.ProductRetrievalService;
import com.nthn.storevision.service.product.ProductUpdateService;
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

/** Version 1 implementation of {@link ProductController}. */
@Tag(name = "Product", description = "Product management endpoints")
@Slf4j
@RestController
@RequestMapping(value = EndpointDefinitions.PRODUCTS, version = "v1")
@RequiredArgsConstructor
public class ProductControllerV1 implements ProductController {

  private final ProductCreationService creationService;
  private final ProductDeletionService deletionService;
  private final ProductRetrievalService retrievalService;
  private final ProductUpdateService updateService;

  @Operation(summary = "Create a new product", description = OperationDescription.PRODUCT_CREATION)
  @ApiResponse(responseCode = ResponseCode.CREATED, description = ResponseDescription.CREATED)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(
      responseCode = ResponseCode.CONFLICT,
      description = ResponseDescription.PRODUCT_NAME_EXISTS_IN_CATEGORY)
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @Override
  public ProductDTO createProduct(@Valid @RequestBody final ProductDTO dto) {
    log.debug("POST /products");
    return creationService.create(dto);
  }

  @Operation(
      summary = "Delete a product by ID",
      description = OperationDescription.PRODUCT_DELETION)
  @ApiResponse(responseCode = ResponseCode.NO_CONTENT, description = ResponseDescription.DELETED)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PreAuthorize("hasRole('ADMIN') or (!#hard and hasRole('MANAGER'))")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  @Override
  public void deleteProduct(
      @Parameter(description = ParameterDescription.PRODUCT_ID) @PathVariable final Long id,
      @Parameter(description = ResponseDescription.HARD_DELETE)
          @RequestParam(defaultValue = "false")
          final boolean hard) {
    if (hard) {
      log.debug("DELETE /products/{}?hard=true", id);
      deletionService.permanentlyDelete(id);
    } else {
      log.debug("DELETE /products/{}", id);
      deletionService.markAsDeleted(id);
    }
  }

  @Operation(
      summary = "Retrieve active products (paginated)",
      description = OperationDescription.PRODUCT_LIST)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @PreAuthorize("isAuthenticated()")
  @GetMapping
  @Override
  public PageDTO<ProductDTO> getAllProducts(
      @Parameter(description = ParameterDescription.PAGE_INDEX)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_PAGE)
          final int page,
      @Parameter(description = ParameterDescription.PAGE_SIZE)
          @RequestParam(defaultValue = DefaultRequestParam.DEFAULT_SIZE)
          final int size,
      @Parameter(description = ParameterDescription.PRODUCT_NAME_FILTER)
          @RequestParam(required = false)
          final String name,
      @Parameter(description = ParameterDescription.PRODUCT_CATEGORY_FILTER)
          @RequestParam(required = false)
          final String category) {
    log.debug(
        "GET /products — page: {}, size: {}, name: {}, category: {}", page, size, name, category);
    return retrievalService.findProductsPage(page, size, name, category);
  }

  @Operation(
      summary = "Retrieve a product by ID",
      description = OperationDescription.PRODUCT_GET_BY_ID)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER','STAFF')")
  @GetMapping("/{id}")
  @Override
  public ProductDTO getProductById(
      @Parameter(description = ParameterDescription.PRODUCT_ID) @PathVariable final Long id) {
    log.debug("GET /products/{}", id);
    return retrievalService.findProductDTOById(id);
  }

  @Operation(
      summary = "Restore a soft-deleted product",
      description = OperationDescription.PRODUCT_RESTORE)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.RESTORED)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
  @PatchMapping("/{id}")
  @Override
  public ProductDTO restoreProduct(
      @Parameter(description = ParameterDescription.PRODUCT_ID) @PathVariable final Long id,
      @Valid @RequestBody final ProductRestoreRequest request) {
    log.debug("PATCH /products/{}", id);
    deletionService.restore(id);
    return retrievalService.findProductDTOById(id);
  }

  @Operation(summary = "Update a product by ID", description = OperationDescription.PRODUCT_UPDATE)
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ApiResponse(
      responseCode = ResponseCode.CONFLICT,
      description = ResponseDescription.PRODUCT_NAME_EXISTS_IN_CATEGORY)
  @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
  @PutMapping("/{id}")
  @Override
  public ProductDTO updateProduct(
      @Parameter(description = ParameterDescription.PRODUCT_ID) @PathVariable final Long id,
      @Valid @RequestBody final ProductDTO dto) {
    log.debug("PUT /products/{}", id);
    return updateService.update(id, dto);
  }
}
