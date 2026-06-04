/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 16:49:55
 */

package com.nthn.storevision.repository;

import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.model.User;
import com.nthn.storevision.repository.generic.DeleteRepository;
import com.nthn.storevision.repository.generic.ReadRepository;
import com.nthn.storevision.repository.generic.SaveRepository;
import java.util.Optional;

/**
 * Repository interface for managing {@code User} entities. Combines delete, read, and save
 * operations from parent repository interfaces. Allows searching for users by specific attributes
 * such as email and username.
 */
public interface UserRepository
    extends DeleteRepository<Integer>,
        ReadRepository<UserEntity, Integer>,
        SaveRepository<UserEntity> {

  /**
   * Retrieves a user by their email address.
   *
   * @param email the email address to search for.
   * @return an {@code Optional} containing the {@code User} if found, or an empty {@code Optional}
   *     if not.
   */
  Optional<User> findByEmail(String email);

  /**
   * Retrieves a user by their username.
   *
   * @param username the username to search for.
   * @return an {@code Optional} containing the {@code User} if found, or an empty {@code Optional}
   *     if not.
   */
  Optional<User> findByUsername(String username);
}
