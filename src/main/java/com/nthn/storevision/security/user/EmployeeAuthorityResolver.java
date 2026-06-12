/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeAuthorityResolver.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:38:52
 */

package com.nthn.storevision.security.user;

import com.nthn.storevision.repository.jpa.JpaEmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Resolves the granted authorities of an authenticated account for RBAC.
 *
 * <p>The {@code users} table carries the login identity while the {@code employees} table carries
 * the permission role (ADMIN / MANAGER / STAFF). The two are linked by their shared {@code
 * username}: when an active employee record matches the username, the account receives {@code
 * ROLE_<role>}; otherwise it is a regular customer account and receives {@code ROLE_USER}.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeAuthorityResolver {

  private static final String ROLE_PREFIX = "ROLE_";
  private static final String DEFAULT_ROLE = "%sUSER".formatted(ROLE_PREFIX);

  private final JpaEmployeeRepository jpaEmployeeRepository;

  /**
   * Resolves the authorities for the account with the given username. Soft-deleted employee records
   * are ignored — their accounts fall back to {@code ROLE_USER}.
   *
   * @param username the login username to resolve; must not be {@code null}
   * @return a single-element authority list, either {@code ROLE_<EmployeeRole>} or {@code
   *     ROLE_USER}; never {@code null} or empty
   */
  @Transactional(readOnly = true)
  public List<GrantedAuthority> resolveAuthorities(final @NonNull String username) {
    return jpaEmployeeRepository
        .findByUsername(username)
        .filter(employee -> employee.getDeletedDate().isEmpty())
        .<List<GrantedAuthority>>map(
            employee -> {
              log.debug("Username '{}' resolved to employee role {}", username, employee.getRole());
              return List.of(new SimpleGrantedAuthority("%s%s".formatted(ROLE_PREFIX, employee.getRole().name())));
            })
        .orElseGet(
            () -> {
              log.debug("Username '{}' is not an employee — granting {}", username, DEFAULT_ROLE);
              return List.of(new SimpleGrantedAuthority(DEFAULT_ROLE));
            });
  }
}
