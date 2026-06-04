/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 16:02:35
 */

package com.nthn.storevision.service.user.impl;

import com.nthn.storevision.dto.UserDTO;
import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.mapper.EntityMapper;
import com.nthn.storevision.model.User;
import com.nthn.storevision.repository.jpa.JpaUserRepository;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.user.UserCreationService;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link UserCreationService} that extends {@link AbstractCreatable} to
 * persist {@link UserEntity} instances. {@link JpaUserRepository} is injected directly because
 * {@link com.nthn.storevision.repository.UserRepository} is a read-only domain interface; the JPA
 * repo is the only write-capable abstraction available for users.
 *
 * <p>Since {@link UserEntity} implements {@link User}, the saved entity is returned directly as the
 * {@link User} model without an additional conversion step.
 */
@Service
public class UserCreationServiceImpl
    extends AbstractCreatable<UserDTO, UserEntity, JpaUserRepository>
    implements UserCreationService {

  protected UserCreationServiceImpl(
      final EntityMapper<UserDTO, ?, UserEntity> mapper, final JpaUserRepository repository) {
    super(mapper, repository);
  }
}
