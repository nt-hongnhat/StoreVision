/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryRetrievalServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:24:56
 */

package com.nthn.storevision.service.inventory.impl;

import com.nthn.storevision.dto.inventory.InventoryDTO;
import com.nthn.storevision.dto.product.ProductDTO;
import com.nthn.storevision.dto.stock_movement.StockMovementDTO;
import com.nthn.storevision.dto.stock_movement.StockReceiptDTO;
import com.nthn.storevision.mapper.StockMovementMapper;
import com.nthn.storevision.repository.StockMovementRepository;
import com.nthn.storevision.service.inventory.InventoryRetrievalService;
import com.nthn.storevision.service.product.ProductRetrievalService;
import com.nthn.storevision.util.PaginationUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link InventoryRetrievalService}. All reads are marked read-only.
 * Cross-domain stock queries delegate to {@link ProductRetrievalService} to avoid bypassing the
 * product domain's repository boundary.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryRetrievalServiceImpl implements InventoryRetrievalService {

  private final ProductRetrievalService productRetrievalService;
  private final StockMovementMapper stockMovementMapper;
  private final StockMovementRepository stockMovementRepository;

  /**
   * BR-I04: configurable threshold; products at or below this quantity are considered low-stock.
   */
  @Value("${inventory.low-stock-threshold:10}")
  private int lowStockThreshold;

  /** {@inheritDoc} */
  @Override
  @Transactional(readOnly = true)
  public List<InventoryDTO> findAllInventory() {
    log.debug("Fetching all inventory");
    return productRetrievalService.findAllProductsAsDTO().stream()
        .map(InventoryRetrievalServiceImpl::toInventoryDTO)
        .toList();
  }

  /** {@inheritDoc} */
  @Override
  @Transactional(readOnly = true)
  public InventoryDTO findInventoryByProductId(final Long productId) {
    log.debug("Fetching inventory for productId: {}", productId);
    return toInventoryDTO(productRetrievalService.findProductDTOById(productId));
  }

  @Override
  @Transactional(readOnly = true)
  public List<InventoryDTO> findLowStockProducts() {
    log.debug("Fetching low-stock products (threshold={})", lowStockThreshold);
    return productRetrievalService.findLowStockProducts(lowStockThreshold).stream()
        .map(InventoryRetrievalServiceImpl::toInventoryDTO)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<StockMovementDTO> findMovementsByProductId(final Long productId) {
    log.debug("Fetching movement history for productId: {}", productId);
    return stockMovementRepository.findByProductId(productId).stream()
        .map(stockMovementMapper::toMovementDTO)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public Page<StockReceiptDTO> findReceiptHistory(
      final LocalDateTime from, final LocalDateTime to, final int page, final int size) {
    log.debug(
        "Fetching receipt history — from: {}, to: {}, page: {}, size: {}", from, to, page, size);
    // Ordering is fixed inside the repository query (movementDate DESC) — pass an unsorted page.
    return stockMovementRepository
        .findReceiptsBetween(from, to, PaginationUtil.toPageRequest(page, size, Sort.unsorted()))
        .map(stockMovementMapper::toReceiptDTO);
  }

  private static InventoryDTO toInventoryDTO(final @NonNull ProductDTO dto) {
    return InventoryDTO.builder()
        .productId(dto.getId())
        .productName(dto.getName())
        .sku(dto.getSku())
        .category(dto.getCategory())
        .stockQuantity(dto.getStockQuantity())
        .price(dto.getPrice())
        .build();
  }
}
