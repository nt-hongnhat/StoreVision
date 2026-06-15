/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationRetrievalService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 15:06:17
 */

package com.nthn.storevision.service.notification;

import com.nthn.storevision.dto.notification.NotificationDTO;
import com.nthn.storevision.dto.response.PageDTO;
import com.nthn.storevision.entity.NotificationEntity;
import com.nthn.storevision.service.generic.Readable;

/**
 * Service interface for retrieving {@link NotificationEntity} entities.
 *
 * <p>Extends the {@link Readable} interface to provide common read operations for {@link
 * NotificationEntity} entities.
 */
public interface NotificationRetrievalService extends Readable<NotificationEntity, Long> {

  /**
   * Retrieves a notification by its identifier and maps it to {@link NotificationDTO}.
   *
   * @param id the identifier of the notification to retrieve; must not be {@code null}
   * @return the {@link NotificationDTO} with the specified identifier; never {@code null}
   * @throws com.nthn.storevision.exception.EntityNotFoundException if the notification does not
   *     exist
   */
  NotificationDTO findNotificationDTOById(Long id);

  /**
   * Returns one page of notifications, most recently created first.
   *
   * @param page zero-based page index; negative values are treated as 0
   * @param size page size; clamped to the range [1, 100]
   * @return the requested page of notifications as {@link NotificationDTO}; never {@code null}
   */
  PageDTO<NotificationDTO> findNotifications(int page, int size);
}
