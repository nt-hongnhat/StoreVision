/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 17:13:59
 */

package com.nthn.storevision.service.user.impl;

import com.nthn.storevision.constant.message.UserMessageCode;
import com.nthn.storevision.dto.UserDTO;
import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.mapper.EntityMapperConfig;
import com.nthn.storevision.repository.UserRepository;
import com.nthn.storevision.service.common.AbstractUpdatable;
import com.nthn.storevision.service.user.UserUpdateService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link UserUpdateService} for persisting updates to {@link UserEntity}
 * instances in the database.
 */
@Service
public class UserUpdateServiceImpl
    extends AbstractUpdatable<UserDTO, Integer, UserEntity, UserRepository>
    implements UserUpdateService {

  protected UserUpdateServiceImpl(
      final EntityMapperConfig<UserDTO, Integer, UserEntity> mapper,
      final UserRepository repository) {
    super(mapper, repository);
  }

  @Override
  protected UserEntity getById(final Integer id) {
    return getRepository()
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(UserMessageCode.USER_NOT_FOUND, id));
  }
}
