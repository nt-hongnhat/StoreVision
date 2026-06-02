/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02
 */

package com.nthn.storevision.service.order;

import com.nthn.storevision.dto.OrderDTO;
import java.util.List;

/** Single entry point for all order business operations used by the controller layer. */
public interface OrderService {

  OrderDTO create(OrderDTO dto);

  List<OrderDTO> findAll();

  OrderDTO findById(Long id);

  /** Updates status, orderDate, and totalAmount. The associated customer is not updatable. */
  OrderDTO update(Long id, OrderDTO dto);

  void delete(Long id);
}
