/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JacksonConfig.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 13:52:26
 */

package com.nthn.storevision.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configuration for Jackson ObjectMapper to use snake_case for JSON properties. */
@Configuration
public class JacksonConfig {

  /**
   * Configures and provides a custom ObjectMapper bean. Sets the property naming strategy to
   * SNAKE_CASE.
   *
   * @return A configured ObjectMapper instance.
   */
  @Bean
  public ObjectMapper objectMapper() {
    final ObjectMapper mapper = new ObjectMapper();
    mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    return mapper;
  }
}
