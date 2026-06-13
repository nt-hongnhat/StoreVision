/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerDeletionServiceImplTest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 14:38:59
 */

package com.nthn.storevision.service.customer.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nthn.storevision.exception.CustomerHasPendingOrdersException;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.service.order.OrderRetrievalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for {@link CustomerDeletionServiceImpl}. */
@ExtendWith(MockitoExtension.class)
class CustomerDeletionServiceImplTest {

  private static final Long CUSTOMER_ID = 1L;
  @Mock private OrderRetrievalService orderRetrievalService;
  @Mock private CustomerRepository repository;
  private CustomerDeletionServiceImpl service;

  @Test
  void markAsDeleted_shouldDelegateToSuperWhenNoPendingOrders() {
    when(orderRetrievalService.existsPendingOrderForCustomer(CUSTOMER_ID)).thenReturn(false);

    service.markAsDeleted(CUSTOMER_ID);

    verify(repository).markDeletedById(CUSTOMER_ID);
  }

  @Test
  void markAsDeleted_shouldThrowWhenCustomerHasPendingOrders() {
    when(orderRetrievalService.existsPendingOrderForCustomer(CUSTOMER_ID)).thenReturn(true);

    assertThrows(CustomerHasPendingOrdersException.class, () -> service.markAsDeleted(CUSTOMER_ID));

    verify(repository, never()).markDeletedById(CUSTOMER_ID);
  }

  @Test
  void permanentlyDelete_shouldDelegateToSuperWhenNoPendingOrders() {
    when(orderRetrievalService.existsPendingOrderForCustomer(CUSTOMER_ID)).thenReturn(false);

    service.permanentlyDelete(CUSTOMER_ID);

    verify(repository).deleteById(CUSTOMER_ID);
  }

  @Test
  void permanentlyDelete_shouldThrowWhenCustomerHasPendingOrders() {
    when(orderRetrievalService.existsPendingOrderForCustomer(CUSTOMER_ID)).thenReturn(true);

    assertThrows(
        CustomerHasPendingOrdersException.class, () -> service.permanentlyDelete(CUSTOMER_ID));

    verify(repository, never()).deleteById(CUSTOMER_ID);
  }

  @BeforeEach
  void setUp() {
    service = new CustomerDeletionServiceImpl(repository, orderRetrievalService);
  }
}
