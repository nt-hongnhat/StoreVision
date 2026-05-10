/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: LocaleConfig.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:13:41
 */

package com.nthn.storevision.config;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * Configuration class for setting up internationalization (i18n) in the Spring Boot app. This class
 * defines a bean for resolving the locale based on the Accept-Language HTTP header.
 */
@Configuration
public class LocaleConfig {

  /**
   * Creates a {@link LocaleResolver} bean that resolves the locale
   * based on the {@code Accept-Language} header.
   *
   * @return an instance of {@link AcceptHeaderLocaleResolver} configured with a default locale.
   */
  @Bean
  public LocaleResolver localeResolver() {
    // Create an instance of AcceptHeaderLocaleResolver
    final AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();

    // Set the default locale to US English
    resolver.setDefaultLocale(Locale.US);

    // Return the configured LocaleResolver
    return resolver;
  }
}
