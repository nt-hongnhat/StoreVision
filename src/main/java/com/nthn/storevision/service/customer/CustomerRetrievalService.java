/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerRetrievalService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:00:59
 */

package com.nthn.storevision.service.customer;

import com.nthn.storevision.dto.CustomerDTO;
import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.service.generic.Readable;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Service interface for retrieving {@link CustomerEntity} entities.
 *
 * <p>Extends the {@link Readable} interface to provide common read operations for {@link
 * CustomerEntity} entities.
 */
public interface CustomerRetrievalService extends Readable<CustomerEntity, Long> {

  /**
   * Retrieves all customers and maps them to {@link CustomerDTO}.
   *
   * @return a list of all {@link CustomerDTO}s.
   */
  List<CustomerDTO> findAllCustomersAsDTO();

  /**
   * Finds a customer by their email address and maps the result using the provided mapping
   * function.
   *
   * @param email the email address of the customer to retrieve
   * @param mapper a function to map the retrieved customer instance into the desired type
   * @param <T> the return type after applying the mapper function
   * @return an {@code Optional} containing the mapped result if the customer is found, or an empty
   *     {@code Optional} if no customer corresponds to the given email
   */
  <T> Optional<T> findByEmail(String email, Function<? super CustomerEntity, T> mapper);

  /**
   * Retrieves a customer by its identifier and maps it to {@link CustomerDTO}.
   *
   * @param id the identifier of the customer to be retrieved.
   * @return the {@link CustomerDTO} with the specified identifier, or null if not found.
   */
  CustomerDTO findCustomerDTOById(Long id);

  /**
   * Retrieves the order history of a customer, most recent first. The customer must exist —
   * otherwise an {@link com.nthn.storevision.exception.EntityNotFoundException} is thrown.
   *
   * @param customerId the customer whose orders are queried; must not be {@code null}
   * @return list of the customer's active orders as {@link OrderDTO}s; never {@code null}, may be
   *     empty
   */
  List<OrderDTO> findOrderHistory(Long customerId);
}
