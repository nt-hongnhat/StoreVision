/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaUserRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:42:08
 */

package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.repository.generic.SaveRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for {@link UserEntity}. Provides standard JPA operations and custom query methods
 * for User entities.
 *
 * <p>Extends {@link SaveRepository} so that {@link
 * com.nthn.storevision.service.common.AbstractCreatable} can accept it as a typed repository
 * without depending on {@code JpaRepository} directly.
 */
@Repository
public interface JpaUserRepository
    extends JpaRepository<UserEntity, Integer>, SaveRepository<UserEntity> {

  /**
   * Returns all active (non-soft-deleted) users.
   *
   * @return the list of active users; never {@code null}
   */
  List<UserEntity> findByDeletedDateIsNull();

  /**
   * Finds a {@link UserEntity} by its email.
   *
   * @param email the email address of the user to retrieve; must not be null.
   * @return an {@link Optional} containing the found {@link UserEntity} if present, or an empty
   *     {@link Optional} if no user exists with the specified email.
   */
  Optional<UserEntity> findByEmail(String email);

  /**
   * Finds a {@link UserEntity} by its username.
   *
   * @param username the username of the user to retrieve; must not be null.
   * @return an {@link Optional} containing the found {@link UserEntity} if present, or an empty
   *     {@link Optional} if no user exists with the specified username.
   */
  Optional<UserEntity> findByUsername(String username);
}
