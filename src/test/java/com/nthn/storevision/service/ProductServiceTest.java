package com.nthn.storevision.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.service.order.OrderRetrievalService;
import com.nthn.storevision.service.product.ProductCreationService;
import com.nthn.storevision.service.product.ProductDeletionService;
import com.nthn.storevision.service.product.ProductRetrievalService;
import com.nthn.storevision.service.product.ProductUpdateService;
import com.nthn.storevision.service.product.impl.ProductServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @Mock private ProductCreationService creationService;
  @Mock private ProductDeletionService deletionService;
  @Mock private OrderRetrievalService orderRetrievalService;
  @Mock private ProductRetrievalService retrievalService;
  @Mock private ProductUpdateService updateService;

  @InjectMocks private ProductServiceImpl productService;

  private ProductDTO sampleDTO;

  @BeforeEach
  void setUp() {
    sampleDTO =
        ProductDTO.builder()
            .id(1L)
            .name("Laptop")
            .category("Điện tử")
            .price(new BigDecimal("1200.00"))
            .stockQuantity(10)
            .build();
  }

  @Test
  void create_delegatesToCreationService() {
    when(creationService.create(sampleDTO)).thenReturn(sampleDTO);

    final ProductDTO result = productService.create(sampleDTO);

    assertThat(result).isEqualTo(sampleDTO);
    verify(creationService).create(sampleDTO);
  }

  @Test
  void findAll_delegatesToRetrievalService() {
    when(retrievalService.findAllProductsAsDTO()).thenReturn(List.of(sampleDTO));

    final List<ProductDTO> result = productService.findAll();

    assertThat(result).containsExactly(sampleDTO);
    verify(retrievalService).findAllProductsAsDTO();
  }

  @Test
  void findById_delegatesToRetrievalService() {
    when(retrievalService.findProductDTOById(1L)).thenReturn(sampleDTO);

    final ProductDTO result = productService.findById(1L);

    assertThat(result).isEqualTo(sampleDTO);
    verify(retrievalService).findProductDTOById(1L);
  }

  @Test
  void update_delegatesToUpdateService() {
    when(updateService.update(1L, sampleDTO)).thenReturn(sampleDTO);

    final ProductDTO result = productService.update(1L, sampleDTO);

    assertThat(result).isEqualTo(sampleDTO);
    verify(updateService).update(1L, sampleDTO);
  }

  @Test
  void delete_noPendingOrders_softDeletesWithoutWarning() {
    when(orderRetrievalService.existsPendingOrderForProduct(1L)).thenReturn(false);

    productService.delete(1L);

    verify(deletionService).markAsDeleted(1L);
    verify(orderRetrievalService).existsPendingOrderForProduct(1L);
  }

  @Test
  void delete_withPendingOrders_stillSoftDeletesAndLogsWarning() {
    when(orderRetrievalService.existsPendingOrderForProduct(1L)).thenReturn(true);

    productService.delete(1L);

    verify(deletionService).markAsDeleted(1L);
  }

  @Test
  void permanentlyDelete_delegatesToDeletionService() {
    productService.permanentlyDelete(1L);

    verify(deletionService).permanentlyDelete(1L);
  }

  @Test
  void restore_delegatesToDeletionService() {
    productService.restore(1L);

    verify(deletionService).restore(1L);
  }

  @Test
  void search_delegatesToRetrievalService() {
    when(retrievalService.search("Lap", "Điện tử")).thenReturn(List.of(sampleDTO));

    final List<ProductDTO> result = productService.search("Lap", "Điện tử");

    assertThat(result).containsExactly(sampleDTO);
    verify(retrievalService).search("Lap", "Điện tử");
  }
}
