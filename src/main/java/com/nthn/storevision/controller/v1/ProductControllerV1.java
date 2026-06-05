/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ProductControllerV1.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 16:09:18
 */

package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.ResponseCode;
import com.nthn.storevision.constant.api.ResponseDescription;
import com.nthn.storevision.controller.ProductController;
import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.service.product.ProductCreationService;
import com.nthn.storevision.service.product.ProductDeletionService;
import com.nthn.storevision.service.product.ProductRetrievalService;
import com.nthn.storevision.service.product.ProductUpdateService;
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

/** Version 1 implementation of {@link ProductController}. */
@Tag(name = "Product", description = "Product management endpoints")
@Slf4j
@RestController
@RequestMapping(value = "/products", version = "v1")
@RequiredArgsConstructor
public class ProductControllerV1 implements ProductController {

  private final ProductCreationService creationService;
  private final ProductDeletionService deletionService;
  private final ProductRetrievalService retrievalService;
  private final ProductUpdateService updateService;

  @Operation(
      summary = "Create a new product",
      description =
          "Creates a new product. Name + category must be unique among active records (BR-P01)."
              + " Price must be > 0 (BR-P02). stockQuantity must be >= 0 (BR-P03).")
  @ApiResponse(responseCode = ResponseCode.CREATED, description = ResponseDescription.CREATED)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(
      responseCode = ResponseCode.CONFLICT,
      description = ResponseDescription.PRODUCT_NAME_EXISTS_IN_CATEGORY)
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  @Override
  public ProductDTO createProduct(@Valid @RequestBody final ProductDTO dto) {
    log.debug("POST /products");
    return creationService.create(dto);
  }

  @Operation(
      summary = "Soft-delete a product by ID",
      description =
          "Marks the product as deleted. If active PENDING orders reference this product,"
              + " a warning is logged but the deletion still proceeds (BR-P04).")
  @ApiResponse(responseCode = ResponseCode.NO_CONTENT, description = ResponseDescription.DELETED)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  @Override
  public void deleteProduct(
      @Parameter(description = ResponseDescription.PRODUCT_ID) @PathVariable final Long id) {
    log.debug("DELETE /products/{}", id);
    deletionService.markAsDeleted(id);
  }

  @Operation(
      summary = "Retrieve all active products",
      description = "Returns a list of all non-deleted product records.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping
  @Override
  public List<ProductDTO> getAllProducts() {
    log.debug("GET /products");
    return retrievalService.findAllProductsAsDTO();
  }

  @Operation(
      summary = "Retrieve a product by ID",
      description = "Returns the product record with the specified ID.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @GetMapping("/{id}")
  @Override
  public ProductDTO getProductById(
      @Parameter(description = ResponseDescription.PRODUCT_ID) @PathVariable final Long id) {
    log.debug("GET /products/{}", id);
    return retrievalService.findProductDTOById(id);
  }

  @Operation(
      summary = "Permanently delete a product by ID",
      description = "Physically removes the product record from the database. Admin only.")
  @ApiResponse(responseCode = ResponseCode.NO_CONTENT, description = ResponseDescription.DELETED)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}/permanent")
  @Override
  public void permanentlyDeleteProduct(
      @Parameter(description = ResponseDescription.PRODUCT_ID) @PathVariable final Long id) {
    log.debug("DELETE /products/{}/permanent", id);
    deletionService.permanentlyDelete(id);
  }

  @Operation(
      summary = "Restore a soft-deleted product",
      description = "Clears the soft-delete markers, making the product active again.")
  @ApiResponse(
      responseCode = ResponseCode.NO_CONTENT,
      description = "Product restored successfully")
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PatchMapping("/{id}/restore")
  @Override
  public void restoreProduct(
      @Parameter(description = ResponseDescription.PRODUCT_ID) @PathVariable final Long id) {
    log.debug("PATCH /products/{}/restore", id);
    deletionService.restore(id);
  }

  @Operation(
      summary = "Search active products",
      description =
          "Filters active products by optional name (case-insensitive, partial match)"
              + " and optional category (exact match). Omit a parameter to skip that filter.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping("/search")
  @Override
  public List<ProductDTO> searchProducts(
      @Parameter(description = "Partial product name (case-insensitive)")
          @RequestParam(required = false)
          final String name,
      @Parameter(description = "Exact category name") @RequestParam(required = false)
          final String category) {
    log.debug("GET /products/search — name: {}, category: {}", name, category);
    return retrievalService.search(name, category);
  }

  @Operation(
      summary = "Update a product by ID",
      description =
          "Updates all mutable fields of an existing product. Name + category must remain unique"
              + " among active records, excluding this product's own record (BR-P01).")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(
      responseCode = ResponseCode.BAD_REQUEST,
      description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ApiResponse(
      responseCode = ResponseCode.CONFLICT,
      description = ResponseDescription.PRODUCT_NAME_EXISTS_IN_CATEGORY)
  @PutMapping("/{id}")
  @Override
  public ProductDTO updateProduct(
      @Parameter(description = ResponseDescription.PRODUCT_ID) @PathVariable final Long id,
      @Valid @RequestBody final ProductDTO dto) {
    log.debug("PUT /products/{}", id);
    return updateService.update(id, dto);
  }
}
