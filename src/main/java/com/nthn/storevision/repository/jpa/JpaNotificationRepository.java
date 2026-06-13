/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaNotificationRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 15:00:00
 */

package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.NotificationEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** JPA Repository for {@link NotificationEntity}. Provides standard queries. */
@Repository
public interface JpaNotificationRepository extends JpaRepository<NotificationEntity, Long> {

  /**
   * Returns all active (non-soft-deleted) notifications.
   *
   * @return the list of active notifications; never {@code null}
   */
  List<NotificationEntity> findByDeletedDateIsNull();

  /**
   * Returns one page of active (non-soft-deleted) notifications.
   *
   * @param pageable page request carrying index, size and sort; must not be {@code null}
   * @return the requested page of active notifications; never {@code null}
   */
  Page<NotificationEntity> findByDeletedDateIsNull(Pageable pageable);
}
