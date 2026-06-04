/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02
 */

package com.nthn.storevision.service.orderitem.impl;

import com.nthn.storevision.dto.OrderItemDTO;
import com.nthn.storevision.service.orderitem.OrderItemCreationService;
import com.nthn.storevision.service.orderitem.OrderItemDeletionService;
import com.nthn.storevision.service.orderitem.OrderItemRetrievalService;
import com.nthn.storevision.service.orderitem.OrderItemService;
import com.nthn.storevision.service.orderitem.OrderItemUpdateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Aggregates all order-item operation services into a single entry point for the controller layer.
 * Order and product associations are intentionally excluded from updates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

  private final OrderItemCreationService creationService;
  private final OrderItemDeletionService deletionService;
  private final OrderItemRetrievalService retrievalService;
  private final OrderItemUpdateService updateService;

  @Override
  @Transactional
  public OrderItemDTO create(final @NonNull OrderItemDTO dto) {
    log.debug("Creating order item for order id: {}", dto.getOrderId());
    final OrderItemDTO created = creationService.create(dto);
    log.debug("Order item created with id: {}", created.getId());
    return created;
  }

  @Override
  @Transactional(readOnly = true)
  public List<OrderItemDTO> findAll() {
    log.debug("Fetching all order items");
    return retrievalService.findAllOrderItemsAsDTO();
  }

  @Override
  @Transactional(readOnly = true)
  public OrderItemDTO findById(final Long id) {
    log.debug("Fetching order item by id: {}", id);
    return retrievalService.findOrderItemDTOById(id);
  }

  @Override
  @Transactional
  public OrderItemDTO update(final Long id, final OrderItemDTO dto) {
    log.debug("Updating order item id: {}", id);
    final OrderItemDTO updated = updateService.update(id, dto);
    log.debug("Order item id: {} updated — quantity: {}", id, updated.getQuantity());
    return updated;
  }

  @Override
  @Transactional
  public void delete(final Long id) {
    log.debug("Deleting order item id: {}", id);
    deletionService.markAsDeleted(id);
    log.debug("Order item id: {} marked as deleted", id);
  }
}
