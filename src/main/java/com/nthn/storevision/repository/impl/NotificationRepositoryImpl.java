/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationRepositoryImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 15:00:00
 */

package com.nthn.storevision.repository.impl;

import com.nthn.storevision.entity.NotificationEntity;
import com.nthn.storevision.repository.NotificationRepository;
import com.nthn.storevision.repository.jpa.JpaNotificationRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the {@link NotificationRepository} interface. This class acts as an adapter,
 * delegating calls to the underlying {@link JpaNotificationRepository}.
 */
@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

  private final JpaNotificationRepository jpaRepository;

  @Override
  public List<NotificationEntity> findAll() {
    return jpaRepository.findByDeletedDateIsNull();
  }

  @Override
  public Page<NotificationEntity> findAllActive(final Pageable pageable) {
    return jpaRepository.findByDeletedDateIsNull(pageable);
  }

  @Override
  public Optional<NotificationEntity> findById(final Long id) {
    return jpaRepository.findById(id);
  }

  @Override
  public boolean isExistsById(final Long id) {
    return jpaRepository.existsById(id);
  }

  @Override
  public NotificationEntity save(final NotificationEntity notification) {
    return jpaRepository.save(notification);
  }
}
