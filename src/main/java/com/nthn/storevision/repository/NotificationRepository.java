/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 15:00:00
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.NotificationEntity;
import com.nthn.storevision.repository.generic.ReadRepository;
import com.nthn.storevision.repository.generic.SaveRepository;

/**
 * Repository interface for managing {@link NotificationEntity} instances. Defines read and save
 * operations for notifications.
 */
public interface NotificationRepository
    extends ReadRepository<NotificationEntity, Long>, SaveRepository<NotificationEntity> {}
