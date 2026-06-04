/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerRepositoryImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:42:28
 */

package com.nthn.storevision.repository.impl;

import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.model.Customer;
import com.nthn.storevision.repository.CustomerRepository;
import com.nthn.storevision.repository.jpa.JpaCustomerRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the {@link CustomerRepository} interface. This class acts as an adapter,
 * delegating calls to the underlying {@link JpaCustomerRepository}.
 */
@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

  private final JpaCustomerRepository jpaRepository;

  @Override
  public void deleteById(final Long id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public void markDeletedById(final Long id) {
    findById(id)
        .ifPresent(
            entity -> {
              entity.setDeleted(true);
              save(entity);
            });
  }

  @Override
  public void restoreById(final Long id) {
    findById(id)
        .ifPresent(
            entity -> {
              entity.setDeletedBy(null).setDeletedDate(null);
              save(entity);
            });
  }

  @Override
  public boolean existsById(final Long id) {
    return jpaRepository.existsById(id);
  }

  @Override
  public List<CustomerEntity> findAll() {
    return jpaRepository.findAll();
  }

  @Override
  public Optional<CustomerEntity> findById(final Long id) {
    return jpaRepository.findById(id);
  }

  @Override
  public Optional<CustomerEntity> findByEmail(final String email) {
    return jpaRepository.findByEmail(email);
  }

  @Override
  public CustomerEntity save(final CustomerEntity customer) {
    return jpaRepository.save(customer);
  }
}
