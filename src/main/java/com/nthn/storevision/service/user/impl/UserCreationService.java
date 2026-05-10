/*
 * Copyright (c) 2025 Hong Nhat. All rights reserved.
 *
 * Project: Book Thymeleaf
 * Module: Book Thymeleaf Main
 * File: UserCreationService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2025-05-18 23:10:22
 */

package com.nthn.storevision.service.user.impl;

import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.model.User;
import com.nthn.storevision.service.common.AbstractCreatable;
import com.nthn.storevision.service.generic.Creatable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Service for creating and persisting {@link User} entities. This class extends {@link
 * AbstractCreatable} to provide a generic implementation of the {@link Creatable} interface for
 * {@link User} entities. It uses a {@link JpaRepository} to manage database interactions.
 *
 * @see com.nthn.storevision.service.common.AbstractCreatable
 * @see com.nthn.storevision.service.generic.Creatable
 * @see JpaRepository
 */
@Service
public class UserCreationService extends AbstractCreatable<User, Integer, UserEntity>
    implements Creatable<User, Integer> {

  /**
   * Constructs a {@code UserCreationService} with the specified repository for managing {@link
   * UserEntity} instances.
   *
   * @param repository the repository used for persisting and managing {@link UserEntity} instances
   */
  public UserCreationService(final JpaRepository<UserEntity, Integer> repository) {

    super(repository);
  }
}
