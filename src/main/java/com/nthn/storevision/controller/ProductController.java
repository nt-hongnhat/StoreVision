package com.nthn.storevision.controller;

import com.nthn.storevision.dto.ProductDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface ProductController {
    ResponseEntity<List<ProductDTO>> getAllProducts();
    ResponseEntity<ProductDTO> getProductById(Long id);
    ResponseEntity<ProductDTO> createProduct(ProductDTO productDTO);
    ResponseEntity<ProductDTO> updateProduct(Long id, ProductDTO productDTO);
    ResponseEntity<Void> deleteProduct(Long id);
}
