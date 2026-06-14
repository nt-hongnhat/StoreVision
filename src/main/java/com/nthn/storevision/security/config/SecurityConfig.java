/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: SecurityConfig.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 15:07:04
 */

package com.nthn.storevision.security.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.nthn.storevision.constant.ApiPathConstants;
import com.nthn.storevision.security.filter.UserInfoFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for Spring Security. This class sets up the security filters, authentication
 * providers, and access rules for the application.
 *
 * <p>RBAC rules follow the permission matrix of the system design (section 3): ADMIN has full
 * access, MANAGER manages catalog/inventory/orders/customers, STAFF creates orders and looks up
 * data, and plain authenticated users (customers, {@code ROLE_USER}) may only search products and
 * view loyalty details. BR-A04: STAFF cannot reach any {@code /analytics} endpoint. Products
 * (3.1) and Inventory (3.2) only require authentication at the path-matcher level; their
 * fine-grained per-role rules are enforced via {@code @PreAuthorize} on the controller methods
 * (see {@link com.nthn.storevision.controller.v1.ProductControllerV1} and {@link
 * com.nthn.storevision.controller.v1.InventoryControllerV1}).
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private static final String ADMIN = "ADMIN";
  private static final String MANAGER = "MANAGER";
  private static final String STAFF = "STAFF";

  private final AuthenticationSuccessHandler oAuth2SuccessHandler;

  /**
   * Provides a {@link BCryptPasswordEncoder} bean for encoding passwords.
   *
   * @return A {@link PasswordEncoder} instance using the BCrypt algorithm.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Configures the security filter chain for HTTP requests. Defines access rules, OAuth2 login,
   * form login, and adds custom filters.
   *
   * @param http The {@link HttpSecurity} object to configure.
   * @return A {@link SecurityFilterChain} instance.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) {

    http.authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        ApiPathConstants.PUBLIC_ALL.getValue(),
                        ApiPathConstants.LOGIN_ALL.getValue(),
                        "/oauth2/**",
                        "/css/**",
                        "/js/**",
                        ApiPathConstants.API_DOCS.getValue(),
                        ApiPathConstants.SWAGGER_UI.getValue(),
                        "/swagger-ui.html",
                        ApiPathConstants.ACTUATOR.getValue())
                    .permitAll()

                    // Products (3.1) and Inventory (3.2): every endpoint requires
                    // authentication; fine-grained role checks are enforced via
                    // @PreAuthorize on the individual controller methods
                    .requestMatchers("/products/**")
                    .authenticated()
                    .requestMatchers("/inventory/**")
                    .authenticated()

                    // Orders (3.3): STAFF creates, views by id and completes;
                    // listing and cancelling are ADMIN/MANAGER
                    .requestMatchers(HttpMethod.POST, "/orders")
                    .hasAnyRole(ADMIN, MANAGER, STAFF)
                    .requestMatchers(HttpMethod.PATCH, "/orders/*/complete")
                    .hasAnyRole(ADMIN, MANAGER, STAFF)
                    .requestMatchers(HttpMethod.PATCH, "/orders/*/cancel")
                    .hasAnyRole(ADMIN, MANAGER)
                    .requestMatchers(HttpMethod.GET, "/orders")
                    .hasAnyRole(ADMIN, MANAGER)
                    .requestMatchers(HttpMethod.GET, "/orders/*")
                    .hasAnyRole(ADMIN, MANAGER, STAFF)
                    .requestMatchers("/orders/**")
                    .hasAnyRole(ADMIN, MANAGER)

                    // Customers & loyalty (3.4): loyalty details are open to every
                    // authenticated account, redemption and creation include STAFF
                    .requestMatchers(HttpMethod.GET, "/customers/*/loyalty")
                    .authenticated()
                    .requestMatchers(HttpMethod.POST, "/customers/*/loyalty/redeem")
                    .hasAnyRole(ADMIN, MANAGER, STAFF)
                    .requestMatchers(HttpMethod.GET, "/customers/*/orders")
                    .hasAnyRole(ADMIN, MANAGER)
                    .requestMatchers(HttpMethod.POST, "/customers")
                    .hasAnyRole(ADMIN, MANAGER, STAFF)
                    .requestMatchers(HttpMethod.GET, "/customers")
                    .hasAnyRole(ADMIN, MANAGER)
                    .requestMatchers(HttpMethod.GET, "/customers/*")
                    .hasAnyRole(ADMIN, MANAGER, STAFF)
                    .requestMatchers("/customers/**")
                    .hasAnyRole(ADMIN, MANAGER)

                    // Employees (1.2): account administration is ADMIN-only
                    .requestMatchers("/employees/**")
                    .hasRole(ADMIN)

                    // Analytics (3.5): profit and inventory valuation are ADMIN-only;
                    // BR-A04 — STAFF cannot reach any analytics endpoint
                    .requestMatchers(
                        HttpMethod.GET, "/analytics/profit", "/analytics/inventory/valuation")
                    .hasRole(ADMIN)
                    .requestMatchers("/analytics/**")
                    .hasAnyRole(ADMIN, MANAGER)

                    // Notifications (3.2.3): low-stock alerts are visible to ADMIN/MANAGER only
                    .requestMatchers("/notifications/**")
                    .hasAnyRole(ADMIN, MANAGER)
                    .anyRequest()
                    .authenticated())
        .oauth2Login(
            configurer ->
                configurer.successHandler(oAuth2SuccessHandler).failureUrl("/login?error=true"))
        .formLogin(withDefaults())
        .addFilterAfter(new UserInfoFilter(), UsernamePasswordAuthenticationFilter.class)
        .csrf(AbstractHttpConfigurer::disable); // Often needed for REST APIs during development

    return http.build();
  }

  /**
   * Configures web security to ignore certain request matchers. This is typically used for static
   * resources or API documentation endpoints that should not be secured.
   *
   * @return A {@link WebSecurityCustomizer} instance.
   */
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web ->
        web.ignoring()
            .requestMatchers(
                ApiPathConstants.SWAGGER_UI.getValue(),
                ApiPathConstants.API_DOCS.getValue(),
                ApiPathConstants.ACTUATOR.getValue());
  }
}
