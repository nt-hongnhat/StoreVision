/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserRepositoryImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:42:28
 */

package com.nthn.storevision.repository.impl;

import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.model.User;
import com.nthn.storevision.repository.UserRepository;
import com.nthn.storevision.repository.jpa.JpaUserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the {@link UserRepository} interface. Delegates to {@link JpaUserRepository}
 * and adapts the {@link UserEntity} to the {@link User} model interface.
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final JpaUserRepository jpaUserRepository;

  @Override
  public void deleteById(final Integer id) {
    jpaUserRepository.deleteById(id);
  }

  @Override
  public void markDeletedById(final Integer id) {
    jpaUserRepository
        .findById(id)
        .ifPresent(
            entity -> {
              entity.setDeleted(true);
              jpaUserRepository.save(entity);
            });
  }

  @Override
  public void restoreById(final Integer id) {
    jpaUserRepository
        .findById(id)
        .ifPresent(
            entity -> {
              entity.setDeletedBy(null);
              entity.setDeletedDate(null);
              entity.setDeleted(false);
              jpaUserRepository.save(entity);
            });
  }

  @Override
  public boolean existsById(final Integer id) {
    return jpaUserRepository.existsById(id);
  }

  @Override
  public List<User> findAll() {
    return jpaUserRepository.findAll().stream().<User>map(u -> u).toList();
  }

  @Override
  public Optional<User> findById(final Integer id) {
    return jpaUserRepository.findById(id).map(User.class::cast);
  }

  @Override
  public Optional<User> findByEmail(final String email) {
    return jpaUserRepository.findByEmail(email).map(User.class::cast);
  }

  @Override
  public Optional<User> findByUsername(final String username) {
    return jpaUserRepository.findByUsername(username).map(User.class::cast);
  }

  @Override
  public User save(final User user) {
    return jpaUserRepository.save((UserEntity) user);
  }
}
