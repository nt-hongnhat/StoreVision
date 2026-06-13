/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationUpdateServiceImplTest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 15:00:00
 */

package com.nthn.storevision.service.notification.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nthn.storevision.dto.NotificationDTO;
import com.nthn.storevision.entity.NotificationEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.NotificationMapper;
import com.nthn.storevision.repository.NotificationRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/** Unit tests for {@link NotificationUpdateServiceImpl}. */
@ExtendWith(MockitoExtension.class)
class NotificationUpdateServiceImplTest {

  private static final Long NOTIFICATION_ID = 1L;

  @Mock private NotificationMapper notificationMapper;
  @Mock private NotificationRepository notificationRepository;

  private NotificationUpdateServiceImpl service;

  @Test
  void markAsRead_shouldSetReadFlagAndReturnMappedDTO() {
    final NotificationEntity entity =
        NotificationEntity.builder().id(NOTIFICATION_ID).read(false).build();
    final NotificationDTO dto = NotificationDTO.builder().id(NOTIFICATION_ID).read(true).build();
    when(notificationRepository.findById(NOTIFICATION_ID)).thenReturn(Optional.of(entity));
    when(notificationRepository.save(entity)).thenReturn(entity);
    when(notificationMapper.toDTO(entity)).thenReturn(dto);

    final NotificationDTO result = service.markAsRead(NOTIFICATION_ID);

    assertThat(result).isEqualTo(dto);
    assertThat(entity.isRead()).isTrue();
    verify(notificationRepository).save(entity);
  }

  @Test
  void markAsRead_shouldThrowWhenNotFound() {
    when(notificationRepository.findById(NOTIFICATION_ID)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> service.markAsRead(NOTIFICATION_ID));

    verify(notificationRepository, never()).save(any());
  }

  @BeforeEach
  void setUp() {
    service = new NotificationUpdateServiceImpl(notificationMapper, notificationRepository);
  }
}
