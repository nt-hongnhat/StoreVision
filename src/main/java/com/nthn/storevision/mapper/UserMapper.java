/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 15:18:45
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.dto.UserDTO;
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
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    config = BaseEntityMapperConfig.class)
public interface UserMapper extends EntityMapper<UserDTO, Integer, UserEntity> {}
