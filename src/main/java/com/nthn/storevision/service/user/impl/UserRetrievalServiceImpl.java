/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserRetrievalServiceImpl.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 17:07:38
 */

package com.nthn.storevision.service.user.impl;

import com.nthn.storevision.constant.message.LocalizedMessageCode;
import com.nthn.storevision.constant.message.UserMessageCode;
import com.nthn.storevision.model.User;
import com.nthn.storevision.repository.UserRepository;
import com.nthn.storevision.service.common.AbstractReadable;
import com.nthn.storevision.service.user.UserRetrievalService;
import java.util.Optional;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for retrieving {@link User} entities. Extends {@link AbstractReadable} to provide read
 * operations for {@link User} entities.
 */
@Slf4j
@Service
public class UserRetrievalServiceImpl
    extends AbstractReadable<User, Integer, UserRepository>
    implements UserRetrievalService {

  /**
   * Constructs a {@code UserRetrievalServiceImpl} with the specified repository.
   *
   * @param repository the repository used for accessing {@link User} entities
   */
  public UserRetrievalServiceImpl(final UserRepository repository) {
    super(repository);
  }

  /**
   * Finds a user by their email address and maps the result using the provided mapping function.
   * This operation is read-only and transactional.
   *
   * @param email the email address of the user to retrieve
   * @param mapper a function to map the retrieved user instance into the desired type
   * @param <T> the return type after applying the mapper function
   * @return an {@code Optional} containing the mapped result if the user is found, or an empty
   *     {@code Optional} if no user corresponds to the given email
   */
  @Transactional(readOnly = true)
  @Override
  public <T> Optional<T> findByEmail(final String email, final Function<? super User, T> mapper) {
    log.debug("Fetching user by email: {}", email);
    return getRepository().findByEmail(email).map(mapper);
  }

  /**
   * Returns the message code for when a user is not found.
   *
   * @return The message code for "user not found".
   */
  @Override
  protected LocalizedMessageCode getNotFoundMessageCode() {
    return UserMessageCode.USER_NOT_FOUND;
  }
}
