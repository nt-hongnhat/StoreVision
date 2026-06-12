/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderRetrievalService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:25:02
 */

package com.nthn.storevision.service.order;

import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.dto.response.PageDTO;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.service.generic.Readable;

import java.util.List;

/**
 * Service interface for retrieving {@link OrderEntity} entities.
 *
 * <p>Extends the {@link Readable} interface to provide common read operations for {@link
 * OrderEntity} entities.
 */
public interface OrderRetrievalService extends Readable<OrderEntity, Long> {

  /**
   * BR-C02: returns {@code true} if the given customer still has any active (non-deleted) PENDING
   * order. Used by the customer deletion flow to reject the deletion.
   *
   * @param customerId the customer ID to check; must not be {@code null}
   * @return {@code true} if at least one pending order belongs to this customer
   */
  boolean existsPendingOrderForCustomer(Long customerId);

  /**
   * BR-P04: returns {@code true} if any active (non-deleted) PENDING order contains the given
   * product. Used by the product soft-delete flow to emit a warning before deletion.
   *
   * @param productId the product ID to check
   * @return {@code true} if at least one pending order references this product
   */
  boolean existsPendingOrderForProduct(Long productId);

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

  /**
   * Retrieves all active (non-deleted) orders of a customer, most recent first, mapped to {@link
   * OrderDTO}.
   *
   * @param customerId the customer ID to query; must not be {@code null}
   * @return list of the customer's orders; never {@code null}, may be empty
   */
  List<OrderDTO> findOrdersByCustomerId(Long customerId);

  /**
   * Retrieves one page of active orders ordered by the most recent first, mapped to {@link
   * OrderDTO}. Out-of-range parameters are clamped instead of rejected.
   *
   * @param page zero-based page index; negative values are treated as 0
   * @param size page size; clamped to a sane maximum
   * @return the requested page of orders; never {@code null}
   */
  PageDTO<OrderDTO> findOrdersPage(int page, int size);
}
