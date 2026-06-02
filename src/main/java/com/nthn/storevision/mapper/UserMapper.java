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
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Mapper interface for converting between {@link User} and {@link UserEntity}. This interface uses
 * MapStruct to generate the implementation, facilitating the mapping between domain models and
 * database entities in the context of user-related data.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

  /**
   * Converts a {@link User} domain model to a {@link UserEntity} database entity.
   *
   * @param user The {@link User} instance to be converted.
   * @return The converted {@link UserEntity} instance.
   */
  UserEntity toEntity(User user);

  /**
   * Converts a {@link UserEntity} instance to a {@link User} domain model instance.
   *
   * @param userEntity The {@link UserEntity} object to be converted.
   * @return The converted {@link User} domain model instance.
   */
  User toModel(UserEntity userEntity);
}
