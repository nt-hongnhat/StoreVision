/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12
 */

package com.nthn.storevision.service.order;

import com.nthn.storevision.dto.order.OrderDTO;
import com.nthn.storevision.dto.request.OrderCreationRequest;
import com.nthn.storevision.service.generic.Creatable;
import org.jspecify.annotations.NonNull;

/**
 * Service interface for creating and persisting orders.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see Creatable
 */
public interface OrderCreationService extends Creatable<OrderDTO> {

  /**
   * Creates a sales order in {@code PENDING} status from the given request.
   *
   * <p>The flow enforces the order-creation business rules: the customer and every product must
   * exist and be active, stock availability is verified without being deducted (BR-O01 — deduction
   * happens only on completion), the total amount is computed server-side (BR-O03), and each
   * item's unit price is locked from the product's current price (BR-O04). The request must carry
   * at least one item (BR-O05, validated at the controller boundary).
   *
   * @param request the order details; must not be {@code null}
   * @return the created order including its persisted order items; never {@code null}
   */
  OrderDTO createOrder(@NonNull OrderCreationRequest request);
}
