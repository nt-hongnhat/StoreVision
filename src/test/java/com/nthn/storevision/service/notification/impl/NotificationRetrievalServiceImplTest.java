/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationRetrievalServiceImplTest.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 15:00:00
 */

package com.nthn.storevision.service.notification.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nthn.storevision.dto.notification.NotificationDTO;
import com.nthn.storevision.dto.response.PageDTO;
import com.nthn.storevision.entity.NotificationEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.NotificationMapper;
import com.nthn.storevision.repository.NotificationRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/** Unit tests for {@link NotificationRetrievalServiceImpl}. */
@ExtendWith(MockitoExtension.class)
class NotificationRetrievalServiceImplTest {

  private static final Long NOTIFICATION_ID = 1L;

  @Mock private NotificationMapper notificationMapper;
  @Mock private NotificationRepository notificationRepository;

  private NotificationRetrievalServiceImpl service;

  @Test
  void findNotificationDTOById_shouldReturnMappedDTO() {
    final NotificationEntity entity = NotificationEntity.builder().id(NOTIFICATION_ID).build();
    final NotificationDTO dto = NotificationDTO.builder().id(NOTIFICATION_ID).build();
    when(notificationRepository.findById(NOTIFICATION_ID)).thenReturn(Optional.of(entity));
    when(notificationMapper.toDTO(entity)).thenReturn(dto);

    final NotificationDTO result = service.findNotificationDTOById(NOTIFICATION_ID);

    assertThat(result).isEqualTo(dto);
  }

  @Test
  void findNotificationDTOById_shouldThrowWhenNotFound() {
    when(notificationRepository.findById(NOTIFICATION_ID)).thenReturn(Optional.empty());

    assertThrows(
        EntityNotFoundException.class, () -> service.findNotificationDTOById(NOTIFICATION_ID));
  }

  @Test
  void findNotifications_shouldReturnMappedPage() {
    final NotificationEntity entity = NotificationEntity.builder().id(NOTIFICATION_ID).build();
    final NotificationDTO dto = NotificationDTO.builder().id(NOTIFICATION_ID).build();
    final Pageable pageable = PageRequest.of(0, 20);
    when(notificationRepository.findAllActive(any(Pageable.class)))
        .thenReturn(new PageImpl<>(List.of(entity), pageable, 1));
    when(notificationMapper.toDTO(entity)).thenReturn(dto);

    final PageDTO<NotificationDTO> result = service.findNotifications(0, 20);

    assertThat(result.content()).containsExactly(dto);
    assertThat(result.totalElements()).isEqualTo(1);
  }

  @BeforeEach
  void setUp() {
    service = new NotificationRetrievalServiceImpl(notificationRepository, notificationMapper);
  }
}
