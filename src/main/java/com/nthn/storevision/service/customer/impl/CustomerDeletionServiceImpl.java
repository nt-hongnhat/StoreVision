/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerDeletionServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:00:54
 */

package com.nthn.storevision.service.customer.impl;

import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.exception.CustomerHasPendingOrdersException;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.service.common.AbstractDeletable;
import com.nthn.storevision.service.customer.CustomerDeletionService;
import com.nthn.storevision.service.order.OrderRetrievalService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link CustomerDeletionService} that soft-deletes
 * {@link CustomerEntity} instances by setting the {@code isDeleted} flag, which triggers
 * {@code CustomAuditingEntityListener} to populate {@code deletedBy} and {@code deletedDate}.
 */
@Service
public class CustomerDeletionServiceImpl
    extends AbstractDeletable<CustomerEntity, Long, CustomerRepository>
    implements CustomerDeletionService {

  protected CustomerDeletionServiceImpl(final CustomerRepository repository) {
    super(repository);
  @Override
  @Transactional
  public void markAsDeleted(final Long id) {
    rejectIfPendingOrdersExist(id);
    super.markAsDeleted(id);
  }

  @Override
  @Transactional
  public void permanentlyDelete(final Long id) {
    rejectIfPendingOrdersExist(id);
    super.permanentlyDelete(id);
  }

  /** BR-C02: deletion is rejected while the customer still has PENDING orders. */
  private void rejectIfPendingOrdersExist(final Long customerId) {
    if (orderRetrievalService.existsPendingOrderForCustomer(customerId)) {
      log.warn("BR-C02 violation: customer id {} still has PENDING orders", customerId);
      throw new CustomerHasPendingOrdersException(customerId);
    }
  }
}
