package com.nthn.storevision.service;

import com.nthn.storevision.application.port.in.ProductUseCase;
import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.domain.model.Product;
import com.nthn.storevision.mapper.ProductMapper;
import com.nthn.storevision.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductUseCase productUseCase;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(new BigDecimal("1200.00"))
                .stockQuantity(10)
                .build();

        productDTO = ProductDTO.builder()
                .id(1L)
                .name("Laptop")
                .price(new BigDecimal("1200.00"))
                .stockQuantity(10)
                .build();
    }

    @Test
    void getAllProducts_ShouldReturnList() {
        when(productUseCase.getAllProducts()).thenReturn(Collections.singletonList(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        List<ProductDTO> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getName());
        verify(productUseCase, times(1)).getAllProducts();
    }

    @Test
    void getProductById_WhenExists_ShouldReturnProduct() {
        when(productUseCase.getProductById(1L)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_WhenNotExists_ShouldThrowException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> productService.getProductById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() {
        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(product);
        when(productUseCase.createProduct(any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(productDTO);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(productUseCase, times(1)).createProduct(any(Product.class));
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() {
        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(product);
        when(productUseCase.updateProduct(eq(1L), any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO result = productService.updateProduct(1L, productDTO);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(productUseCase, times(1)).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    void deleteProduct_ShouldDelegateToUseCase() {
        doNothing().when(productUseCase).deleteProduct(1L);

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}
