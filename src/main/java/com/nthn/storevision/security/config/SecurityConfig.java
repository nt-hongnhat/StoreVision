/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: SecurityConfig.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 17:03:38
 */

package com.nthn.storevision.security.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.nthn.storevision.security.filter.UserInfoFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

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
                        "/public/**",
                        "/login/**",
                        "/oauth2/**",
                        "/css/**",
                        "/js/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/actuator/**")
                    .permitAll()
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
    return web -> web.ignoring().requestMatchers("/swagger-ui/**", "/v3/api-docs/**");
  }
}
