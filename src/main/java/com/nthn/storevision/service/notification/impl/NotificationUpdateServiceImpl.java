/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 14:54:35
 */

package com.nthn.storevision.service.notification.impl;

import com.nthn.storevision.constant.message.NotificationMessageCode;
import com.nthn.storevision.dto.notification.NotificationDTO;
import com.nthn.storevision.entity.NotificationEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.NotificationMapper;
import com.nthn.storevision.repository.NotificationRepository;
import com.nthn.storevision.service.notification.NotificationUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Default implementation of {@link NotificationUpdateService}. */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationUpdateServiceImpl implements NotificationUpdateService {

  private final NotificationMapper notificationMapper;
  private final NotificationRepository notificationRepository;

  /** {@inheritDoc} */
  @Override
  @Transactional
  public NotificationDTO markAsRead(final @NonNull Long id) {
    log.debug("Marking notification as read: id={}", id);
    final NotificationEntity notification =
        notificationRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(NotificationMessageCode.NOT_FOUND, id));
    notification.setRead(true);
    return notificationMapper.toDTO(notificationRepository.save(notification));
  }
}
