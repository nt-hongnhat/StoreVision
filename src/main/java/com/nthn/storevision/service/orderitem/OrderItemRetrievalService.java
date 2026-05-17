/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemRetrievalService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:22:33
 */

package com.nthn.storevision.service.orderitem;

import com.nthn.storevision.dto.OrderItemDTO;
import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.service.generic.Readable;
import org.springframework.modulith.NamedInterface;

import java.util.List;

/**
 * Service interface for retrieving {@link OrderItemEntity} entities.
 *
 * <p>Extends the {@link Readable} interface to provide common read operations for {@link
 * OrderItemEntity} entities.
 */
@NamedInterface
public interface OrderItemRetrievalService extends Readable<OrderItemEntity, Long> {

  /**
   * Retrieves all order items and maps them to {@link OrderItemDTO}.
   *
   * @return a list of all {@link OrderItemDTO}s.
   */
  List<OrderItemDTO> findAllOrderItemsAsDTO();

  /**
   * Retrieves an order item by its identifier and maps it to {@link OrderItemDTO}.
   *
   * @param id the identifier of the order item to be retrieved.
   * @return the {@link OrderItemDTO} with the specified identifier, or null if not found.
   */
  OrderItemDTO findOrderItemDTOById(Long id);
}
