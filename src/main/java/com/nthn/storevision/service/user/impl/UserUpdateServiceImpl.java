/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserUpdateServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 11:21:37
 */

package com.nthn.storevision.service.user.impl;

import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.model.User;
import com.nthn.storevision.service.user.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation of {@link UserUpdateService} for persisting updates to {@link UserEntity}
 * instances in the database.
 */
@Service
@RequiredArgsConstructor
public class UserUpdateServiceImpl implements UserUpdateService {

  private final JpaRepository<UserEntity, Integer> repository;

  @Transactional
  @Override
  public User update(final Long id, final User user) {
    repository.save((UserEntity) user);
    return user;
  }
}
