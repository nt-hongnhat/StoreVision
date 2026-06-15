/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerController.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:27:23
 */

package com.nthn.storevision.controller;

import com.nthn.storevision.dto.customer.CustomerDTO;
import com.nthn.storevision.dto.loyalty_point.LoyaltyDetailsDTO;
import com.nthn.storevision.dto.order.OrderDTO;
import com.nthn.storevision.dto.voucher.VoucherDTO;
import com.nthn.storevision.dto.request.RedeemPointsRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * API contract for customer management and loyalty endpoints.
 *
 * @see com.nthn.storevision.controller.v1.CustomerControllerV1
 */
public interface CustomerController {

  /**
   * Creates a new customer. The email must be unique across the whole system, including
   * soft-deleted records (BR-C01).
   *
   * @param dto the customer data to persist
   * @return the created customer
   */
  CustomerDTO createCustomer(@Valid CustomerDTO dto);

  /**
   * Soft-deletes a customer by ID. Rejected while the customer still has PENDING orders (BR-C02).
   *
   * @param id the ID of the customer to delete
   */
  void deleteCustomer(Long id);

  /**
   * Retrieves one page of active customers ordered by name.
   *
   * @param page zero-based page index
   * @param size page size
   * @return the requested page of customers
   */
  Page<CustomerDTO> getAllCustomers(int page, int size);

  /**
   * Retrieves a customer by ID.
   *
   * @param id the ID of the customer to retrieve
   * @return the matching customer
   */
  CustomerDTO getCustomerById(Long id);

  /**
   * Retrieves the order history of a customer, most recent first.
   *
   * @param id the ID of the customer whose orders are queried
   * @return list of the customer's orders
   */
  List<OrderDTO> getCustomerOrders(Long id);

  /**
   * Retrieves a customer's loyalty overview: tier, point balance, total spending, and the point
   * transaction history.
   *
   * @param id the ID of the customer to query
   * @return the loyalty overview
   */
  LoyaltyDetailsDTO getLoyaltyDetails(Long id);

  /**
   * Redeems loyalty points for a single-use discount voucher. Redemptions exceeding the point
   * balance are rejected (BR-C03).
   *
   * @param id the ID of the customer redeeming points
   * @param request the number of points to redeem
   * @return the minted voucher
   */
  VoucherDTO redeemPoints(Long id, @Valid RedeemPointsRequest request);

  /**
   * Updates an existing customer.
   *
   * @param id the ID of the customer to update
   * @param dto the updated customer data
   * @return the updated customer
   */
  CustomerDTO updateCustomer(Long id, @Valid CustomerDTO dto);
}
