/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02
 */

package com.nthn.storevision.service.orderitem;

import com.nthn.storevision.dto.OrderItemDTO;
import java.util.List;

/** Single entry point for all order-item business operations used by the controller layer. */
public interface OrderItemService {

  OrderItemDTO create(OrderItemDTO dto);

  List<OrderItemDTO> findAll();

  OrderItemDTO findById(Long id);

  /** Updates quantity and unitPrice only. Order and product associations are not updatable. */
  OrderItemDTO update(Long id, OrderItemDTO dto);

  void delete(Long id);
}
