/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OAuth2SuccessHandler.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 17:05:07
 */

package com.nthn.storevision.security.oauth;

import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.factory.user.UserFactory;
import com.nthn.storevision.model.User;
import com.nthn.storevision.repository.jpa.JpaUserRepository;
import com.nthn.storevision.security.user.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * Custom authentication success handler for OAuth2 logins.
 * This handler is responsible for processing successful OAuth2 authentications,
 * creating or updating user information in the database, and setting up the security context.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

  private static final String EMAIL = "email";
  private static final String ROLE_USER = "ROLE_USER";

  private final UserFactory googleAuthUserFactory;
  private final JpaUserRepository jpaRepository;

  /**
   * Called when an authentication attempt is successful.
   * This method handles the post-authentication logic for OAuth2 users,
   * including user provisioning (creating or updating user in DB) and
   * setting the security context.
   *
   * @param request The HTTP request.
   * @param response The HTTP response.
   * @param authentication The successful authentication object.
   * @throws IOException If an I/O error occurs during redirection.
   */
  @Override
  public void onAuthenticationSuccess(
      final HttpServletRequest request,
      final HttpServletResponse response,
      @NonNull final Authentication authentication)
      throws IOException {
    log.debug("Authentication success for user: {}", authentication.getName());

    User persistUser = null;
    final Collection<? extends GrantedAuthority> authorities =
        List.of(new SimpleGrantedAuthority(ROLE_USER));

    if (authentication instanceof OAuth2AuthenticationToken token) {
      final Map<String, Object> attributes = token.getPrincipal().getAttributes();
      final String email = String.valueOf(attributes.get(EMAIL));
      persistUser =
          jpaRepository
              .findByEmail(email)
              .map(this::updateUserInfo)
              .orElseGet(() -> createUserFromAttributes(attributes));
    }

    if (persistUser != null) {
      configureUserAuthentication(authorities, persistUser);
    }

    log.debug("Redirecting after successful authentication.");
    response.sendRedirect("/api/v1/products"); // Changed to a meaningful redirect for StoreVision
  }

  /**
   * Creates a new user from the provided OAuth2 attributes.
   *
   * @param attributes A map of user attributes obtained from the OAuth2 provider.
   * @return The newly created and saved {@link User} object.
   */
  private User createUserFromAttributes(final Map<String, Object> attributes) {
    final User user = googleAuthUserFactory.create(attributes);
    jpaRepository.save((UserEntity) user);
    log.debug("User created successfully: {}", user);
    return user;
  }

  /**
   * Updates an existing user's information.
   *
   * @param user The {@link User} object to update.
   * @return The updated and saved {@link User} object.
   */
  private User updateUserInfo(final User user) {
    jpaRepository.save((UserEntity) user);
    log.debug("User updated successfully: {}", user);
    return user;
  }

  /**
   * Configures the Spring Security context with the authenticated user's details.
   *
   * @param authorities The granted authorities for the user.
   * @param persistUser The persisted {@link User} object.
   */
  private static void configureUserAuthentication(
      final Collection<? extends GrantedAuthority> authorities, final User persistUser) {
    final UserPrincipal userPrincipal = new UserPrincipal(authorities, persistUser);
    final UsernamePasswordAuthenticationToken newAuth =
        new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(newAuth);
  }
}
