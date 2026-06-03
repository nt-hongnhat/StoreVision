/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 20:27:46
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.model.User;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Mapper interface for converting between {@link User} and {@link UserEntity}. This interface uses
 * MapStruct to generate the implementation, facilitating the mapping between domain models and
 * database entities in the context of user-related data.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, config = BaseEntityMapperConfig.class)
public interface UserMapper {

  /**
   * Converts a {@link User} domain model to a {@link UserEntity} database entity. Audit fields are
   * excluded because they are managed automatically by {@code CustomAuditingEntityListener} — see
   * {@link BaseEntityMapperConfig#toEntity(User)}.
   *
   * @param user The {@link User} instance to be converted.
   * @return The converted {@link UserEntity} instance.
   */
  @InheritConfiguration
  UserEntity toEntity(User user);
}
