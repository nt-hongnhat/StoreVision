/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerRetrievalServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:19:00
 */

package com.nthn.storevision.service.customer.impl;

import com.nthn.storevision.constant.message.CustomerMessageCode;
import com.nthn.storevision.constant.message.LocalizedMessageCode;
import com.nthn.storevision.dto.customer.CustomerDTO;
import com.nthn.storevision.dto.order.OrderDTO;
import com.nthn.storevision.dto.response.PageDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.CustomerMapper;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.service.common.AbstractReadable;
import com.nthn.storevision.service.customer.CustomerRetrievalService;
import com.nthn.storevision.service.order.OrderRetrievalService;
import com.nthn.storevision.util.PaginationUtil;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for retrieving {@link CustomerEntity} entities. Extends {@link AbstractReadable} to
 * provide read operations for {@link CustomerEntity} entities.
 */
@Slf4j
@Service
public class CustomerRetrievalServiceImpl
    extends AbstractReadable<CustomerEntity, Long, CustomerRepository>
    implements CustomerRetrievalService {

  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "name");

  private final CustomerMapper customerMapper;
  private final OrderRetrievalService orderRetrievalService;

  /**
   * Constructs a {@code CustomerRetrievalServiceImpl} with the specified repository and mappers.
   *
   * @param repository the repository used for accessing {@link CustomerEntity} entities
   * @param customerMapper the mapper used for converting between entity and DTO
   * @param orderRetrievalService the order domain service backing the order-history lookup
   */
  public CustomerRetrievalServiceImpl(
      final CustomerRepository repository,
      final CustomerMapper customerMapper,
      final OrderRetrievalService orderRetrievalService) {
    super(repository);
    this.customerMapper = customerMapper;
    this.orderRetrievalService = orderRetrievalService;
  }

  /**
   * Retrieves all customers and maps them to {@link CustomerDTO}.
   *
   * @return a list of all {@link CustomerDTO}s.
   */
  @Override
  @Transactional(readOnly = true)
  public List<CustomerDTO> findAllCustomersAsDTO() {
    log.debug("Fetching all customers as DTO");
    return findAll(customerMapper::toDTO);
  }

  /**
   * Finds a customer by their email address and maps the result using the provided mapping
   * function. This operation is read-only and transactional.
   *
   * @param email the email address of the customer to retrieve
   * @param mapper a function to map the retrieved customer instance into the desired type
   * @param <T> the return type after applying the mapper function
   * @return an {@code Optional} containing the mapped result if the customer is found, or an empty
   *     {@code Optional} if no customer corresponds to the given email
   */
  @Override
  @Transactional(readOnly = true)
  public <T> Optional<T> findByEmail(
      final String email, final Function<? super CustomerEntity, T> mapper) {
    log.debug("Fetching customer by email: {}", email);
    return getRepository().findByEmail(email).map(mapper);
  }

  /**
   * Retrieves a customer by its identifier and maps it to {@link CustomerDTO}.
   *
   * @param id the identifier of the customer to be retrieved.
   * @return the {@link CustomerDTO} with the specified identifier, or null if not found.
   */
  @Override
  @Transactional(readOnly = true)
  public CustomerDTO findCustomerDTOById(final Long id) {
    log.debug("Fetching customer DTO by id: {}", id);
    return findById(id, customerMapper::toDTO);
  }

  /** {@inheritDoc} */
  @Override
  @Transactional(readOnly = true)
  public PageDTO<CustomerDTO> findCustomersPage(final int page, final int size) {
    log.debug("Fetching customers page: page={}, size={}", page, size);
    return PageDTO.from(
        getRepository()
            .findAllActive(PaginationUtil.toPageRequest(page, size, DEFAULT_SORT))
            .map(customerMapper::toDTO));
  }

  /** {@inheritDoc} */
  @Override
  @Transactional(readOnly = true)
  public List<OrderDTO> findOrderHistory(final Long customerId) {
    log.debug("Fetching order history for customer id: {}", customerId);
    if (!getRepository().isExistsById(customerId)) {
      throw new EntityNotFoundException(CustomerMessageCode.NOT_FOUND, customerId);
    }
    return orderRetrievalService.findOrdersByCustomerId(customerId);
  }

  /**
   * Returns the message code for when a customer is not found.
   *
   * @return The message code for "customer not found".
   */
  @Override
  protected LocalizedMessageCode getNotFoundMessageCode() {
    return CustomerMessageCode.NOT_FOUND;
  }
}
