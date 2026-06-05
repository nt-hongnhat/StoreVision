/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderRetrievalServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-05 15:41:44
 */

package com.nthn.storevision.service.order.impl;

import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.mapper.OrderMapper;
import com.nthn.storevision.repository.OrderRepository;
import com.nthn.storevision.service.common.AbstractReadable;
import com.nthn.storevision.service.order.OrderRetrievalService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for retrieving {@link OrderEntity} entities. Extends {@link AbstractReadable} to provide
 * read operations for {@link OrderEntity} entities.
 */
@Slf4j
@Service
public class OrderRetrievalServiceImpl extends AbstractReadable<OrderEntity, Long, OrderRepository>
    implements OrderRetrievalService {

  private final OrderMapper orderMapper;

  /**
   * Constructs a {@code OrderRetrievalServiceImpl} with the specified repository and mapper.
   *
   * @param repository the repository used for accessing {@link OrderEntity} entities
   * @param orderMapper the mapper used for converting between entity and DTO
   */
  public OrderRetrievalServiceImpl(
      final OrderRepository repository, final OrderMapper orderMapper) {
    super(repository);
    this.orderMapper = orderMapper;
  }

  /**
   * Retrieves all orders and maps them to {@link OrderDTO}.
   *
   * @return a list of all {@link OrderDTO}s.
   */
  @Override
  @Transactional(readOnly = true)
  public List<OrderDTO> findAllOrdersAsDTO() {
    log.debug("Fetching all orders as DTO");
    return findAll(orderMapper::toDTO);
  }

  /**
   * Retrieves an order by its identifier and maps it to {@link OrderDTO}.
   *
   * @param id the identifier of the order to be retrieved.
   * @return the {@link OrderDTO} with the specified identifier, or null if not found.
   */
  @Override
  @Transactional(readOnly = true)
  public OrderDTO findOrderDTOById(Long id) {
    log.debug("Fetching order DTO by id: {}", id);
    return findById(id, orderMapper::toDTO);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsPendingOrderForProduct(final Long productId) {
    log.debug("Checking pending orders for product id: {}", productId);
    return getRepository().existsPendingOrderForProduct(productId);
  }

  @Override
  protected String getNotFoundMessageCode() {
    return "order.not.found";
  }
}
