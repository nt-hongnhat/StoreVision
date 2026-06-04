/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:11:26
 */

package com.nthn.storevision.service.order.impl;

import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.service.order.OrderCreationService;
import com.nthn.storevision.service.order.OrderDeletionService;
import com.nthn.storevision.service.order.OrderRetrievalService;
import com.nthn.storevision.service.order.OrderService;
import com.nthn.storevision.service.order.OrderUpdateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Aggregates all order operation services into a single entry point for the controller layer.
 * Customer association is intentionally excluded from updates — change it via a dedicated workflow.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderCreationService creationService;
  private final OrderDeletionService deletionService;
  private final OrderRetrievalService retrievalService;
  private final OrderUpdateService updateService;

  @Override
  @Transactional
  public OrderDTO create(final @NonNull OrderDTO dto) {
    log.debug("Creating order with status: {}", dto.getStatus());
    final OrderDTO created = creationService.create(dto);
    log.debug("Order created with id: {}", created.getId());
    return created;
  }

  @Override
  @Transactional(readOnly = true)
  public List<OrderDTO> findAll() {
    log.debug("Fetching all orders");
    return retrievalService.findAllOrdersAsDTO();
  }

  @Override
  @Transactional(readOnly = true)
  public OrderDTO findById(final Long id) {
    log.debug("Fetching order by id: {}", id);
    return retrievalService.findOrderDTOById(id);
  }

  @Override
  @Transactional
  public OrderDTO update(final Long id, final OrderDTO dto) {
    log.debug("Updating order id: {}", id);
    final OrderDTO updated = updateService.update(id, dto);
    log.debug("Order id: {} updated to status: {}", id, updated.getStatus());
    return updated;
  }

  @Override
  @Transactional
  public void delete(final Long id) {
    log.debug("Deleting order id: {}", id);
    deletionService.markAsDeleted(id);
    log.debug("Order id: {} marked as deleted", id);
  }
}
