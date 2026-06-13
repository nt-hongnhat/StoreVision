/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryAdjustmentServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 17:17:27
 */

package com.nthn.storevision.service.inventory.impl;

import com.nthn.storevision.constant.StockMovementType;
import com.nthn.storevision.constant.message.InventoryMessageCode;
import com.nthn.storevision.dto.InventoryDTO;
import com.nthn.storevision.dto.request.InventoryAdjustmentRequest;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.entity.StockMovementEntity;
import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.exception.EntityAccessException;
import com.nthn.storevision.model.User;
import com.nthn.storevision.repository.StockMovementRepository;
import com.nthn.storevision.service.inventory.InventoryAdjustmentService;
import com.nthn.storevision.service.product.ProductRetrievalService;
import com.nthn.storevision.service.product.ProductUpdateService;
import java.time.LocalDateTime;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link InventoryAdjustmentService}. Handles adjusting product stock
 * quantities and recording corresponding {@link StockMovementType#ADJUSTMENT} movements.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryAdjustmentServiceImpl implements InventoryAdjustmentService {

  private final AuditorAware<User> auditorAware;
  private final ProductRetrievalService productRetrievalService;
  private final ProductUpdateService productUpdateService;
  private final StockMovementRepository stockMovementRepository;

  /** {@inheritDoc} */
  @Override
  @Transactional
  public InventoryDTO adjustStock(final @NonNull InventoryAdjustmentRequest request) {
    log.debug(
        "Adjusting stock for productId={}, quantity={}", request.productId(), request.quantity());

    final ProductEntity product =
        productRetrievalService.findById(request.productId(), Function.identity());

    final UserEntity performer = resolveCurrentUser();

    // Update product stock
    if (0 < request.quantity()) {
      productUpdateService.addStock(request.productId(), request.quantity());
    } else if (0 > request.quantity()) {
      productUpdateService.deductStock(request.productId(), Math.abs(request.quantity()));
    } else {
      log.info(
          "No stock adjustment needed for productId={} as quantity is 0.", request.productId());
      // Return current inventory if no change
      return productRetrievalService.findInventoryByProductId(request.productId());
    }

    // Record stock movement
    final StockMovementEntity movement =
        StockMovementEntity.builder()
            .product(product)
            .type(StockMovementType.ADJUSTMENT)
            .quantity(request.quantity())
            .performedBy(performer)
            .movementDate(LocalDateTime.now())
            .note(request.note())
            .build();

    stockMovementRepository.save(movement);
    log.info(
        "ADJUSTMENT movement recorded: productId={}, quantity={}, new stock={}",
        request.productId(),
        request.quantity(),
        product.getStockQuantity() + request.quantity());

    // Return the updated inventory details
    return productRetrievalService.findInventoryByProductId(request.productId());
  }

  @NonNull
  private UserEntity resolveCurrentUser() {
    return (UserEntity)
        auditorAware
            .getCurrentAuditor()
            .orElseThrow(() -> new EntityAccessException(InventoryMessageCode.PERFORMER_REQUIRED));
  }
}
