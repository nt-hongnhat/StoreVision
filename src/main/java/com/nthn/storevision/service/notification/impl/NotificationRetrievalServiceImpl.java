/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationRetrievalServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 15:06:39
 */

package com.nthn.storevision.service.notification.impl;

import com.nthn.storevision.constant.message.LocalizedMessageCode;
import com.nthn.storevision.constant.message.NotificationMessageCode;
import com.nthn.storevision.dto.notification.NotificationDTO;
import com.nthn.storevision.entity.NotificationEntity;
import com.nthn.storevision.mapper.NotificationMapper;
import com.nthn.storevision.repository.NotificationRepository;
import com.nthn.storevision.service.common.AbstractReadable;
import com.nthn.storevision.service.notification.NotificationRetrievalService;
import com.nthn.storevision.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for retrieving {@link NotificationEntity} entities. Extends {@link AbstractReadable} to
 * provide read operations for {@link NotificationEntity} entities.
 */
@Slf4j
@Service
public class NotificationRetrievalServiceImpl
    extends AbstractReadable<NotificationEntity, Long, NotificationRepository>
    implements NotificationRetrievalService {

  private final NotificationMapper notificationMapper;

  /**
   * Constructs a {@code NotificationRetrievalServiceImpl} with the specified repository and
   * mapper.
   *
   * @param repository the repository used for accessing {@link NotificationEntity} entities
   * @param notificationMapper the mapper used for converting between entity and DTO
   */
  public NotificationRetrievalServiceImpl(
      final NotificationRepository repository, final NotificationMapper notificationMapper) {
    super(repository);
    this.notificationMapper = notificationMapper;
  }

  /** {@inheritDoc} */
  @Override
  @Transactional(readOnly = true)
  public NotificationDTO findNotificationDTOById(final Long id) {
    log.debug("Fetching notification DTO by id: {}", id);
    return findById(id, notificationMapper::toDTO);
  }

  /** {@inheritDoc} */
  @Override
  @Transactional(readOnly = true)
  public Page<NotificationDTO> findNotifications(final int page, final int size) {
    log.debug("Fetching notifications: page={}, size={}", page, size);
    final var pageable = PaginationUtil.toPageRequest(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
    return getRepository().findAllActive(pageable).map(notificationMapper::toDTO);
  }

  /** {@inheritDoc} */
  @Override
  protected LocalizedMessageCode getNotFoundMessageCode() {
    return NotificationMessageCode.NOT_FOUND;
  }
}
