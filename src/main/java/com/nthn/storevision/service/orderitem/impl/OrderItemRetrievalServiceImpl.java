/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemRetrievalServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 17:14:42
 */

package com.nthn.storevision.service.orderitem.impl;

import com.nthn.storevision.constant.message.LocalizedMessageCode;
import com.nthn.storevision.constant.message.OrderItemMessageCode;
import com.nthn.storevision.dto.OrderItemDTO;
import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.mapper.OrderItemMapper;
import com.nthn.storevision.repository.OrderItemRepository; // Changed from JpaOrderItemRepository
import com.nthn.storevision.service.common.AbstractReadable;
import com.nthn.storevision.service.orderitem.OrderItemRetrievalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for retrieving {@link OrderItemEntity} entities. Extends {@link AbstractReadable} to provide read
 * operations for {@link OrderItemEntity} entities.
 */
@Slf4j
@Service
public class OrderItemRetrievalServiceImpl
    extends AbstractReadable<OrderItemEntity, Long, OrderItemRepository> // Changed from JpaOrderItemRepository
    implements OrderItemRetrievalService {

  private final OrderItemMapper orderItemMapper;

  /**
   * Constructs a {@code OrderItemRetrievalServiceImpl} with the specified repository and mapper.
   *
   * @param repository the repository used for accessing {@link OrderItemEntity} entities
   * @param orderItemMapper the mapper used for converting between entity and DTO
   */
  public OrderItemRetrievalServiceImpl(final OrderItemRepository repository, final OrderItemMapper orderItemMapper) { // Changed from JpaOrderItemRepository
    super(repository);
    this.orderItemMapper = orderItemMapper;
  }

  /**
   * Retrieves all order items and maps them to {@link OrderItemDTO}.
   *
   * @return a list of all {@link OrderItemDTO}s.
   */
  @Override
  @Transactional(readOnly = true)
  public List<OrderItemDTO> findAllOrderItemsAsDTO() {
    log.debug("Fetching all order items as DTO");
    return findAll(orderItemMapper::toDTO);
  }

  /**
   * Retrieves an order item by its identifier and maps it to {@link OrderItemDTO}.
   *
   * @param id the identifier of the order item to be retrieved.
   * @return the {@link OrderItemDTO} with the specified identifier, or null if not found.
   */
  @Override
  @Transactional(readOnly = true)
  public OrderItemDTO findOrderItemDTOById(Long id) {
    log.debug("Fetching order item DTO by id: {}", id);
    return findById(id, orderItemMapper::toDTO);
  }

  /**
   * Returns the message code for when an order item is not found.
   *
   * @return The message code for "order item not found".
   */
  @Override
  protected LocalizedMessageCode getNotFoundMessageCode() {
    return OrderItemMessageCode.NOT_FOUND;
  }
}
