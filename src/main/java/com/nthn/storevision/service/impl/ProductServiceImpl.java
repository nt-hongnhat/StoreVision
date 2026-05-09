package com.nthn.storevision.service.impl;

import com.nthn.storevision.application.port.in.ProductUseCase;
import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.domain.model.Product;
import com.nthn.storevision.mapper.ProductMapper;
import com.nthn.storevision.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductUseCase productUseCase;
    private final ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productUseCase.getAllProducts().stream()
                .map(productMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productUseCase.getProductById(id);
        return productMapper.toDTO(product);
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productUseCase.createProduct(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product updatedProduct = productUseCase.updateProduct(id, productMapper.toEntity(productDTO));
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        productUseCase.deleteProduct(id);
    }
}
