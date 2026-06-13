/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: NotificationMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 14:52:25
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.dto.NotificationDTO;
import com.nthn.storevision.entity.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper interface for converting between {@link NotificationEntity} and {@link NotificationDTO}.
 * This interface uses MapStruct to generate the implementation.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, config = EntityMapperConfig.class)
public interface NotificationMapper
    extends EntityMapperConfig<NotificationDTO, Long, NotificationEntity> {

  @Mapping(target = "createdDate", expression = "java(entity.getCreatedDate().orElse(null))")
  @Override
  NotificationDTO toDTO(NotificationEntity entity);
}
