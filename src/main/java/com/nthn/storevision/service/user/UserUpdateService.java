/*
 * Copyright (c) 2025 Hong Nhat. All rights reserved.
 *
 * Project: Book Thymeleaf
 * Module: Book Thymeleaf Main
 * File: UserUpdateService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2025-05-18 17:22:33
 */

package com.nthn.storevision.service.user;

import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.model.User;
import com.nthn.storevision.service.generic.Updatable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for updating {@link UserEntity} instances in the application.
 *
 * <p>This class implements the {@link Updatable} interface, providing functionality for persisting
 * updates to the {@link UserEntity} objects in the underlying database. It uses a {@link
 * JpaRepository} for database access and is marked as a Spring {@link Service} to enable dependency
 * injection and manage its lifecycle. The {@code update} method is executed within a transactional
 * context, ensuring that changes are committed consistently.
 */
@Service
@RequiredArgsConstructor
public class UserUpdateService implements Updatable<User> {

  private final JpaRepository<UserEntity, Integer> repository;

  /**
   * Updates the provided {@link User} object in the database.
   * The user object is cast to {@link UserEntity} before saving.
   *
   * @param user The {@link User} object to update.
   */
  @Transactional
  @Override
  public void update(final User user) {
    repository.save((UserEntity) user);
  }
}
