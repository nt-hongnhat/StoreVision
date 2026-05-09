package com.nthn.storevision.controller.impl;

import com.nthn.storevision.application.port.in.ProductUseCase;
import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.mapper.ProductMapper;
import com.nthn.storevision.controller.ProductController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductUseCase productUseCase;
    private final ProductMapper productMapper;

    @Override
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productUseCase.getAllProducts().stream()
                .map(productMapper::toDTO)
                .toList());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productMapper.toDTO(productUseCase.getProductById(id)));
    }

    @Override
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(
                productMapper.toDTO(productUseCase.createProduct(productMapper.toEntity(productDTO))),
                HttpStatus.CREATED
        );
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productMapper.toDTO(productUseCase.updateProduct(id, productMapper.toEntity(productDTO))));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
