package com.nthn.storevision.controller.v1;

import com.nthn.storevision.controller.ProductController;
import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing products, implementing version 1 of the ProductController interface.
 * Provides endpoints for CRUD operations on products.
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductControllerV1 implements ProductController {

  private final ProductService productService;

  @Override
  @PostMapping
  public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody final ProductDTO productDTO) {
    return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  @GetMapping
  public ResponseEntity<List<ProductDTO>> getAllProducts() {
    return ResponseEntity.ok(productService.getAllProducts().stream().toList());
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> getProductById(@PathVariable final Long id) {
    return ResponseEntity.ok(productService.getProductById(id));
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<ProductDTO> updateProduct(
      @PathVariable final Long id, @Valid @RequestBody final ProductDTO productDTO) {
    return ResponseEntity.ok(productService.updateProduct(id, productDTO));
  }
}
