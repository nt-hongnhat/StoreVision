/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: SwaggerConfig.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:14:14
 */

package com.nthn.storevision.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Configuration class for Swagger API documentation. This class sets up the
 * {@link OpenAPI} specifications for the application, including security
 * configurations and API grouping.
 */
@PropertySource("classpath:application-authentication.yml")
@Configuration
@EnableWebMvc
public class SwaggerConfig {

  /// The name of the OAuth2 provider used for authentication.
  private static final String GOOGLE = "google";
  private final String authorizationUrl;
  private final String tokenUrl;

  /**
   * Constructs a {@code SwaggerConfig} instance with the specified OAuth2
   * client properties.
   *
   * @param properties the OAuth2 client properties used to configure the
   *                   {@value GOOGLE} provider
   * @throws IllegalStateException if the {@value GOOGLE} provider configuration
   *                               is not found
   */
  public SwaggerConfig(final OAuth2ClientProperties properties) {

    final OAuth2ClientProperties.Provider googleProvider = properties.getProvider()
        .get(GOOGLE);

    if (googleProvider != null) {
      this.authorizationUrl = googleProvider.getAuthorizationUri();
      this.tokenUrl = googleProvider.getTokenUri();
    } else {
      throw new IllegalStateException(
          "Google provider configuration not found");
    }
  }

  /**
   * Configures the actuator API group for Swagger documentation.
   *
   * @return a {@link GroupedOpenApi} instance for the actuator group
   */
  @Bean
  public GroupedOpenApi configureActuatorEndpoints() {

    return GroupedOpenApi.builder().group("actuator")
        .pathsToMatch("/actuator/**").build();
  }

  /**
   * Configures the main API group for Swagger documentation.
   *
   * @return a {@link GroupedOpenApi} instance for the main API group
   */
  @Bean
  public GroupedOpenApi configureApiEndpoints() {

    return GroupedOpenApi.builder().group("v1").displayName("API V1")
        .pathsToMatch("/api/**") // Include only paths matching /api/**
        .build();
  }

  /**
   * Configures the custom {@link OpenAPI} specification for the application.
   *
   * @return an {@link OpenAPI} instance with the application information and
   * security settings
   */
  @Bean
  public OpenAPI configureOpenAPI() {

    return new OpenAPI().info(
            new Info().title("Book Thymeleaf API").version("1.0")
                .description("Book Thymeleaf API")).addSecurityItem(
            new SecurityRequirement().addList("bearerAuth").addList("oauth2"))
        .components(new Components().addSecuritySchemes("bearerAuth",
            new SecurityScheme().type(Type.HTTP).scheme("bearer")
                .bearerFormat("JWT")).addSecuritySchemes("oauth2",
            new SecurityScheme().type(Type.OAUTH2).flows(
                new OAuthFlows().authorizationCode(
                    new OAuthFlow().authorizationUrl(authorizationUrl)
                        .tokenUrl(tokenUrl))))).addServersItem(
            new Server().url("http://localhost:8080/book-thymeleaf/")
                .description(""));
  }

  /**
   * Configures the security API group for Swagger documentation.
   *
   * @return a {@link GroupedOpenApi} instance for the security group
   */
  @Bean
  public GroupedOpenApi configureSecurityEndpoints() {

    return GroupedOpenApi.builder().group("security")
        .pathsToMatch("/login", "/login/**").build();
  }
}
