package com.nthn.storevision.controller.v1;

import com.nthn.storevision.constant.api.ResponseCode;
import com.nthn.storevision.constant.api.ResponseDescription;
import com.nthn.storevision.controller.ProductController;
import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.service.product.ProductService;
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

/**
 * Version 1 implementation of {@link ProductController}.
 */
@Tag(name = "Product", description = "Product management endpoints")
@Slf4j
@RestController
@RequestMapping(value = "/products", version = "v1")
@RequiredArgsConstructor
public class ProductControllerV1 implements ProductController {

  private static final String ID_DESC = "Product ID";

  private final ProductService productService;

  @Operation(
      summary = "Create a new product",
      description = "Creates a new product record and persists it to the database.")
  @ApiResponse(responseCode = ResponseCode.CREATED, description = ResponseDescription.CREATED)
  @ApiResponse(responseCode = ResponseCode.BAD_REQUEST, description = ResponseDescription.INVALID_INPUT)
  @PostMapping
  @Override
  public ProductDTO createProduct(@Valid @RequestBody final ProductDTO dto) {
    log.debug("POST /products");
    return productService.create(dto);
  }

  @Operation(
      summary = "Soft-delete a product by ID",
      description = "Marks the product as deleted without physically removing the record."
          + " The record can be restored later.")
  @ApiResponse(responseCode = ResponseCode.NO_CONTENT, description = ResponseDescription.DELETED)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  @Override
  public void deleteProduct(
      @Parameter(description = ID_DESC) @PathVariable final Long id) {
    log.debug("DELETE /products/{}", id);
    productService.delete(id);
  }

  @Operation(
      summary = "Retrieve all products",
      description = "Returns a list of all active (non-deleted) product records.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @GetMapping
  @Override
  public List<ProductDTO> getAllProducts() {
    log.debug("GET /products");
    return productService.findAll();
  }

  @Operation(
      summary = "Retrieve a product by ID",
      description = "Returns the product record with the specified ID.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @GetMapping("/{id}")
  @Override
  public ProductDTO getProductById(
      @Parameter(description = ID_DESC) @PathVariable final Long id) {
    log.debug("GET /products/{}", id);
    return productService.findById(id);
  }

  @Operation(
      summary = "Update a product by ID",
      description = "Updates the mutable fields (name, price, stockQuantity, category) of an existing product.")
  @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK)
  @ApiResponse(responseCode = ResponseCode.BAD_REQUEST, description = ResponseDescription.INVALID_INPUT)
  @ApiResponse(responseCode = ResponseCode.NOT_FOUND, description = ResponseDescription.NOT_FOUND)
  @PutMapping("/{id}")
  @Override
  public ProductDTO updateProduct(
      @Parameter(description = ID_DESC) @PathVariable final Long id,
      @Valid @RequestBody final ProductDTO dto) {
    log.debug("PUT /products/{}", id);
    return productService.update(id, dto);
  }
}
