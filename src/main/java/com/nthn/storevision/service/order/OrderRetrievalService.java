/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderRetrievalService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:17:03
 */

package com.nthn.storevision.service.order;

import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.service.generic.Readable;
import org.springframework.modulith.NamedInterface;

import java.util.List;

/**
 * Service interface for retrieving {@link OrderEntity} entities.
 *
 * <p>Extends the {@link Readable} interface to provide common read operations for {@link
 * OrderEntity} entities.
 */
@NamedInterface
public interface OrderRetrievalService extends Readable<OrderEntity, Long> {

  /**
   * Retrieves all orders and maps them to {@link OrderDTO}.
   *
   * @return a list of all {@link OrderDTO}s.
   */
  List<OrderDTO> findAllOrdersAsDTO();

  /**
   * Retrieves an order by its identifier and maps it to {@link OrderDTO}.
   *
   * @param id the identifier of the order to be retrieved.
   * @return the {@link OrderDTO} with the specified identifier, or null if not found.
   */
  OrderDTO findOrderDTOById(Long id);
}
