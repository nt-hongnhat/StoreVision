/*
 * Copyright (c) 2025 Hong Nhat. All rights reserved.
 *
 * Project: Book Thymeleaf
 * Module: Book Thymeleaf Main
 * File: UserAuditorAware.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2025-05-18 23:00:55
 */

package com.nthn.storevision.component.audit;

import com.nthn.storevision.component.UserPrincipal;
import com.nthn.storevision.model.User;
import com.nthn.storevision.service.user.UserRetrievalService;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
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
   * The default email address for the administrator account. This constant is used as a fallback
   * when no authenticated user is found and to identify or retrieve the administrator user from the
   * system.
   */
  public static final String DEFAULT_ADMIN_EMAIL = "admin123@gmail.com";

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
  private UserRetrievalService userRetrievalService;

  /**
   * Returns the current auditor (authenticated user).
   * It attempts to retrieve the user from the security context, first checking for a {@link UserPrincipal}
   * and then for a {@link DefaultOAuth2User}. If no authenticated user is found, it falls back to
   * the default admin user.
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

      if (authOptional.get() instanceof final UserPrincipal userPrincipal) {
        final User user = userPrincipal.getUser();

        log.debug(
            "UserPrincipal found: userId={}, email={}",
            null != user ? user.getId() : null,
            null != user ? user.getEmail() : null);

        return Optional.ofNullable(user)
            .filter(u -> null != u.getId())
            .map(u -> userRetrievalService.findById(u.getId(), Function.identity()));
      }

      if (authOptional.get() instanceof final DefaultOAuth2User defaultOAuth2User) {
        return userRetrievalService
            .findByEmail(defaultOAuth2User.getAttribute(EMAIL), Function.identity())
            .or(this::getDefaultAdminUser);
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
    return userRetrievalService.findByEmail(DEFAULT_ADMIN_EMAIL, Function.identity());
  }
}
