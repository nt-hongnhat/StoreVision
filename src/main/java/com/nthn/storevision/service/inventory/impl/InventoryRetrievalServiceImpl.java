/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryRetrievalServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 00:00:00
 */

package com.nthn.storevision.service.inventory.impl;

import com.nthn.storevision.dto.InventoryDTO;
import com.nthn.storevision.dto.ProductDTO;
import com.nthn.storevision.dto.StockMovementDTO;
import com.nthn.storevision.dto.StockReceiptDTO;
import com.nthn.storevision.mapper.StockMovementMapper;
import com.nthn.storevision.repository.StockMovementRepository;
import com.nthn.storevision.service.inventory.InventoryRetrievalService;
import com.nthn.storevision.service.product.ProductRetrievalService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

  /** BR-I04: configurable threshold; products at or below this quantity are considered low-stock. */
  @Value("${inventory.low-stock-threshold:10}")
  private int lowStockThreshold;

  private final ProductRetrievalService productRetrievalService;
  private final StockMovementRepository stockMovementRepository;
  private final StockMovementMapper stockMovementMapper;

  /** {@inheritDoc} */
  @Override
  @Transactional(readOnly = true)
  public List<InventoryDTO> findAllInventory() {
    log.debug("Fetching all inventory");
    return productRetrievalService.findAllProductsAsDTO().stream()
        .map(this::toInventoryDTO)
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
  public List<StockReceiptDTO> findReceiptHistory(
      final LocalDateTime from, final LocalDateTime to) {
    log.debug("Fetching receipt history — from: {}, to: {}", from, to);
    return stockMovementRepository.findReceiptsBetween(from, to).stream()
        .map(stockMovementMapper::toReceiptDTO)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<InventoryDTO> findLowStockProducts() {
    log.debug("Fetching low-stock products (threshold={})", lowStockThreshold);
    return productRetrievalService.findLowStockProducts(lowStockThreshold).stream()
        .map(this::toInventoryDTO)
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

  private InventoryDTO toInventoryDTO(final ProductDTO dto) {
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
