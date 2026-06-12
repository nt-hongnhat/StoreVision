/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeRepositoryImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:07:46
 */

package com.nthn.storevision.repository.impl;

import com.nthn.storevision.entity.EmployeeEntity;
import com.nthn.storevision.repository.EmployeeRepository;
import com.nthn.storevision.repository.jpa.JpaEmployeeRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the {@link EmployeeRepository} interface. This class acts as an adapter,
 * delegating calls to the underlying {@link JpaEmployeeRepository}.
 */
@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {

  private final JpaEmployeeRepository jpaRepository;

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
  public List<EmployeeEntity> findAll() {
    return jpaRepository.findByDeletedDateIsNull();
  }

  @Override
  public Page<EmployeeEntity> findAllActive(final Pageable pageable) {
    return jpaRepository.findByDeletedDateIsNull(pageable);
  }

  @Override
  public Optional<EmployeeEntity> findById(final Long id) {
    return jpaRepository.findById(id);
  }

  @Override
  public boolean isExistsById(final Long id) {
    return jpaRepository.existsById(id);
  }

  @Override
  public Optional<EmployeeEntity> findByUsername(final String username) {
    return jpaRepository.findByUsername(username);
  }

  @Override
  public EmployeeEntity save(final EmployeeEntity employee) {
    return jpaRepository.save(employee);
  }
}
