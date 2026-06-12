/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomUserDetailsService.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:37:51
 */

package com.nthn.storevision.security.user;

import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.repository.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of Spring Security's {@link UserDetailsService}. This service is
 * responsible for loading user-specific data during authentication. RBAC authorities are resolved
 * through {@link EmployeeAuthorityResolver}: employee accounts receive their {@code
 * ROLE_<EmployeeRole>}, everyone else receives {@code ROLE_USER}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final EmployeeAuthorityResolver employeeAuthorityResolver;
  private final JpaUserRepository jpaRepository;

  /**
   * Locates the user based on the username. In this implementation, it tries to find a user by
   * username first, and if not found, then by email.
   *
   * @param username The username (or email) identifying the user whose data is required.
   * @return A {@link UserDetails} object (specifically {@link UserPrincipal}) containing the user's
   *     information.
   * @throws UsernameNotFoundException If the user could not be found or has no granted authorities.
   */
  @Override
  public UserDetails loadUserByUsername(final String username) {
    log.debug("Loading user by username: {}", username);
    final UserEntity user =
        jpaRepository
            .findByUsername(username)
            .or(() -> jpaRepository.findByEmail(username))
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(
                        "User not found with username or email: %s".formatted(username)));
    return new UserPrincipal(
        employeeAuthorityResolver.resolveAuthorities(user.getUsername()), user);
  }
}
