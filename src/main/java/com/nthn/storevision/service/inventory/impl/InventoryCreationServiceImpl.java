/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: InventoryCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 17:02:10
 */

package com.nthn.storevision.service.inventory.impl;

import com.nthn.storevision.constant.message.InventoryMessageCodes;
import com.nthn.storevision.dto.StockReceiptDTO;
import com.nthn.storevision.dto.request.StockReceiptRequest;
import com.nthn.storevision.entity.ProductEntity;
import com.nthn.storevision.entity.StockMovementEntity;
import com.nthn.storevision.constant.StockMovementType;
import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.exception.EntityAccessException;
import com.nthn.storevision.mapper.StockMovementMapper;
import com.nthn.storevision.model.User;
import com.nthn.storevision.repository.StockMovementRepository;
import com.nthn.storevision.service.inventory.InventoryCreationService;
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
 * Default implementation of {@link InventoryCreationService}. Enforces BR-I01 (performed_by
 * mandatory) and BR-I02 (quantity &gt;= 1, validated at request level). All writes run in a single
 * transaction: product stock update + movement record creation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryCreationServiceImpl implements InventoryCreationService {

  private final AuditorAware<User> auditorAware;
  private final ProductRetrievalService productRetrievalService;
  private final ProductUpdateService productUpdateService;
  private final StockMovementMapper stockMovementMapper;
  private final StockMovementRepository stockMovementRepository;

  /** {@inheritDoc} */
  @Override
  @Transactional
  public StockReceiptDTO receiveStock(final @NonNull StockReceiptRequest request) {
    log.debug(
        "Receiving stock: productId={}, quantity={}",
        request.getProductId(),
        request.getQuantity());

    final ProductEntity product =
        productRetrievalService.findById(request.getProductId(), Function.identity());

    final UserEntity performer = resolveCurrentUser();

    productUpdateService.addStock(request.getProductId(), request.getQuantity());

    final StockMovementEntity movement =
        StockMovementEntity.builder()
            .product(product)
            .type(StockMovementType.RECEIPT)
            .quantity(request.getQuantity())
            .unitCost(request.getUnitCost())
            .performedBy(performer)
            .movementDate(LocalDateTime.now())
            .note(request.getNote())
            .build();

    final StockMovementEntity saved = stockMovementRepository.save(movement);
    log.debug("RECEIPT movement created: id={}, productId={}", saved.getId(), product.getId());
    return stockMovementMapper.toReceiptDTO(saved);
  }

  /** {@inheritDoc} */
  @Override
  @Transactional
  public void recordIssue(
      final Long productId, final Integer quantity, final Long referenceOrderId) {
    log.debug(
        "Recording ISSUE: productId={}, quantity={}, orderId={}",
        productId,
        quantity,
        referenceOrderId);

    final ProductEntity product = productRetrievalService.findById(productId, Function.identity());
    final UserEntity performer = resolveCurrentUser();

    final StockMovementEntity movement =
        StockMovementEntity.builder()
            .product(product)
            .type(StockMovementType.ISSUE)
            .quantity(quantity)
            .referenceOrderId(referenceOrderId)
            .performedBy(performer)
            .movementDate(LocalDateTime.now())
            .build();

    stockMovementRepository.save(movement);
    log.debug("ISSUE movement created: productId={}, orderId={}", productId, referenceOrderId);
  }

  @NonNull
  private UserEntity resolveCurrentUser() {
    return (UserEntity)
        auditorAware
            .getCurrentAuditor()
            .orElseThrow(() -> new EntityAccessException(InventoryMessageCodes.PERFORMER_REQUIRED));
  }
}
