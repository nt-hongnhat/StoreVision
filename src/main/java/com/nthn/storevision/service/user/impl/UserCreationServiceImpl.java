/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserCreationServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 11:21:37
 */

package com.nthn.storevision.service.user.impl;

import com.nthn.storevision.entity.UserEntity;
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
 */
@Service
public class UserCreationServiceImpl extends AbstractCreatable<User, Integer, UserEntity>
    implements UserCreationService {

  public UserCreationServiceImpl(final JpaUserRepository repository) {
    super(repository::save);
  }
}
