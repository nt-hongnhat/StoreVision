/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserAuditorAware.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:38:52
 */

package com.nthn.storevision.component.audit.impl;

import com.nthn.storevision.model.User;
import com.nthn.storevision.security.user.UserPrincipal;
import com.nthn.storevision.service.user.UserRetrievalService;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Persistable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

/**
 * Provides the current auditor for auditing purposes in the application. This class implements the
 * {@link AuditorAware} interface to retrieve the current authenticated user.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserAuditorAware implements AuditorAware<User> {

  /**
   * A constant representing the key or attribute name used to reference and retrieve the email
   * field, notably in authentication and auditing processes.
   *
   * <p>This key is typically used when accessing user data within security contexts or when
   * fetching user details (e.g., email addresses) from authentication providers (e.g., OAuth2). It
   * acts as a standard identifier to ensure consistent handling of email attributes across
   * different parts of the application.
   */
  public static final String EMAIL = "email";

  private final ApplicationContext applicationContext;

  @Value("${app.audit.default-admin-email}")
  private String defaultAdminEmail;

  private UserRetrievalService userRetrievalService;

  /**
   * Returns the current auditor (authenticated user). It attempts to retrieve the user from the
   * security context, first checking for a {@link UserPrincipal} and then for a {@link
   * DefaultOAuth2User}. If no authenticated user is found, it falls back to the default admin user.
   *
   * @return an {@link Optional} containing the current {@link User} if authenticated, or an empty
   *     {@link Optional} if not authenticated and no default admin user is found.
   */
  @NonNull
  @Override
  public Optional<User> getCurrentAuditor() {

    log.debug("Getting current auditor...");
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    userRetrievalService = applicationContext.getBean(UserRetrievalService.class);
    // Use Optional to handle the authenticated user retrieval more concisely
    final Optional<Object> authOptional =
        Optional.ofNullable(authentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal);

    if (authOptional.isPresent()) {
      final Object principal = authOptional.get();

      log.debug("Found authentication principal: {}", principal.getClass().getSimpleName());

      switch (authOptional.get()) {
        case final UserPrincipal userPrincipal -> {
          final User user = userPrincipal.getUser();

          log.debug(
              "UserPrincipal found: userId={}, email={}",
              Optional.ofNullable(user).map(Persistable::getId).orElse(null),
              Optional.ofNullable(user).map(User::getEmail).orElse(null));

          return Optional.ofNullable(user)
              .filter(u -> null != u.getId())
              .map(u -> userRetrievalService.findById(u.getId(), Function.identity()));
        }
        case final DefaultOAuth2User defaultOAuth2User -> {
          return userRetrievalService
              .findByEmail(defaultOAuth2User.getAttribute(EMAIL), Function.identity())
              .or(this::getDefaultAdminUser);
        }
        default -> {}
      }
    }

    log.warn("No authenticated user found. Using default admin.");
    return getDefaultAdminUser();
  }

  /**
   * Retrieves the default admin user by searching for a user with the pre-configured admin email.
   *
   * @return an {@code Optional} containing the default admin {@code User} if found, or an empty
   *     {@code Optional} if no user is associated with the admin email.
   */
  private Optional<User> getDefaultAdminUser() {
    return userRetrievalService.findByEmail(defaultAdminEmail, Function.identity());
  }
}
