/*
 * Copyright (c) 2025 Hong Nhat. All rights reserved.
 *
 * Project: Book Thymeleaf
 * Module: Book Thymeleaf Main
 * File: UserRetrievalService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2025-05-18 23:00:55
 */

package com.nthn.storevision.service.user;

import com.nthn.storevision.model.User;
import com.nthn.storevision.service.generic.Readable;
import org.springframework.modulith.NamedInterface;

import java.util.Optional;
import java.util.function.Function;

/**
 * Service interface for retrieving {@link User} entities.
 *
 * <p>Extends the {@link Readable} interface to provide common read operations for {@link
 * User} entities using the {@link com.nthn.storevision.repository.jpa.JpaUserRepository}. Additionally, it provides a method for
 * finding a user by their email.
 */
@NamedInterface
public interface UserRetrievalService extends Readable<User, Integer> {

  /**
   * Finds a user by their email address and maps the result using the provided mapping function.
   *
   * @param email the email address of the user to retrieve
   * @param mapper a function to map the retrieved user instance into the desired type
   * @param <T> the return type after applying the mapper function
   * @return an {@code Optional} containing the mapped result if the user is found, or an empty
   *     {@code Optional} if no user corresponds to the given email
   */
  <T> Optional<T> findByEmail(String email, Function<? super User, T> mapper);
}
