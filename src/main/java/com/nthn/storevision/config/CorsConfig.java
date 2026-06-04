/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CorsConfig.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 10:04:17
 */

package com.nthn.storevision.config;

import com.nthn.storevision.constant.ApiPathConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class that sets up Cross-Origin Resource Sharing (CORS) settings for the web
 * application. Implements the {@code WebMvcConfigurer} interface to customize CORS mappings.
 *
 * <p>This configuration restricts access to specific origins, allows specific HTTP methods, and
 * enables credential support to ensure secure and controlled interactions between the backend and
 * frontend.
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {

  private final WebMvcProperties webMvcProperties;

  @Override
  public void configureApiVersioning(final @NonNull ApiVersionConfigurer configurer) {
    final WebMvcProperties.Apiversion apiversion = webMvcProperties.getApiversion();
    if (null == apiversion || null == apiversion.getUse()) {
      log.debug("API versioning configuration missing in properties, skipping versioning setup");
      return;
    }

    final String requestHeader = apiversion.getUse().getHeader();
    if (null == requestHeader) {
      log.debug("API versioning request header not configured, skipping versioning setup");
      return;
    }

    final String defaultVersion = apiversion.getDefaultVersion();
    log.debug(
        "Configuring API versioning with header resolver [{}], default version: {}",
        requestHeader,
        defaultVersion);

    configurer.setDefaultVersion(defaultVersion);
    configurer.useRequestHeader(requestHeader);
    log.debug("API versioning configuration completed");
  }

  @Override
  public void addCorsMappings(final @NonNull CorsRegistry registry) {
    log.debug("Configuring CORS");
    registry
        .addMapping(ApiPathConstants.API.getValue())
        .allowedOrigins("http://localhost:3000") //
        // React frontend default
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true);
    log.debug("CORS configuration completed: {}", registry);
  }
}
