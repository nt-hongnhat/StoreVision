/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserInfoFilter.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 17:03:38
 */

package com.nthn.storevision.security.filter;

import com.nthn.storevision.security.context.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * A filter that extracts user information from OAuth2 authentication tokens
 * and stores it in a {@link UserContext} for the duration of the request.
 * This allows easy access to user attributes throughout the application.
 */
@NoArgsConstructor
@Slf4j
public class UserInfoFilter extends OncePerRequestFilter {

  /**
   * Performs the filtering logic.
   * Extracts OAuth2 user attributes and sets them in the {@link UserContext}.
   * Ensures that user attributes are reset after the request is processed.
   *
   * @param request The HTTP request.
   * @param response The HTTP response.
   * @param filterChain The filter chain.
   * @throws ServletException If a servlet-specific error occurs.
   * @throws IOException If an I/O error occurs.
   */
  @Override
  protected void doFilterInternal(
      @NonNull final HttpServletRequest request,
      @NonNull final HttpServletResponse response,
      @NonNull final FilterChain filterChain)
      throws ServletException, IOException {

    try {
      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication instanceof OAuth2AuthenticationToken token) {
        final Map<String, Object> attributes = token.getPrincipal().getAttributes();
        UserContext.setUserAttributes(attributes);
        log.debug("Set user attributes for authenticated OAuth2 user: {}", attributes);
      } else {
        log.debug("No OAuth2 authentication found for this request.");
      }

      filterChain.doFilter(request, response);
    } finally {
      UserContext.resetUserAttributes();
      log.debug("User attributes reset after request processing.");
    }
  }
}
