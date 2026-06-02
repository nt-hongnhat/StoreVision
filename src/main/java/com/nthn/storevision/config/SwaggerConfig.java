/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: SwaggerConfig.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 21:15:26
 */

package com.nthn.storevision.config;

import static com.nthn.storevision.constant.ApiPathConstants.ACTUATOR;

import com.nthn.storevision.constant.ApiPathConstants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.security.oauth2.client.autoconfigure.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Configuration class for Swagger API documentation. This class sets up the {@link OpenAPI}
 * specifications for the application, including security configurations and API grouping.
 */
@PropertySource("classpath:application-authentication.yml")
@Configuration
@EnableWebMvc
@Slf4j
public class SwaggerConfig {

  private static final String API_TITLE = "Book Thymeleaf API";
  private static final String BEARER_AUTH = "bearerAuth";
  /// The name of the OAuth2 provider used for authentication.
  private static final String GOOGLE = "google";
  private static final String OAUTH2 = "oauth2";
  private final String authorizationUrl;
  private final String tokenUrl;

  /**
   * Constructs a {@code SwaggerConfig} instance with the specified OAuth2 client properties.
   *
   * @param properties the OAuth2 client properties used to configure the {@value GOOGLE} provider
   * @throws IllegalStateException if the {@value GOOGLE} provider configuration is not found
   */
  public SwaggerConfig(final OAuth2ClientProperties properties) {

    final OAuth2ClientProperties.Provider googleProvider = properties.getProvider().get(GOOGLE);

    if (null != googleProvider) {
      authorizationUrl = googleProvider.getAuthorizationUri();
      tokenUrl = googleProvider.getTokenUri();
    } else {
      throw new IllegalStateException("Google provider configuration not found");
    }
  }

  /**
   * Configures the actuator API group for Swagger documentation.
   *
   * @return a {@link GroupedOpenApi} instance for the actuator group
   */
  @Bean
  public GroupedOpenApi configureActuatorEndpoints() {

    log.debug("Registering Swagger group for actuator endpoints under /actuator/**");
    final GroupedOpenApi api =
        GroupedOpenApi.builder()
            .group("actuator")
            .pathsToMatch(ApiPathConstants.ACTUATOR.getValue())
            .build();
    log.debug("Actuator endpoint group registered successfully");
    return api;
  }

  /**
   * Configures the main API group for Swagger documentation.
   *
   * @return a {@link GroupedOpenApi} instance for the main API group
   */
  @Bean
  public GroupedOpenApi configureApiEndpoints() {

    log.debug("Registering Swagger group for API v1 endpoints under /api/**");
    final GroupedOpenApi api =
        GroupedOpenApi.builder()
            .group("v1")
            .displayName("API V1")
            .pathsToMatch(ApiPathConstants.API.getValue())
            .build();
    log.debug("API v1 endpoint group registered successfully");
    return api;
  }

  /**
   * Configures the custom {@link OpenAPI} specification for the application.
   *
   * @return an {@link OpenAPI} instance with the application information and security settings
   */
  @Bean
  public OpenAPI configureOpenAPI() {

    log.debug("Building OpenAPI specification with Bearer and OAuth2 security schemes");
    final OpenAPI openAPI =
        new OpenAPI()
            .info(new Info().title(API_TITLE).version("1.0").description(API_TITLE))
            .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH).addList(OAUTH2))
            .components(
                new Components()
                    .addSecuritySchemes(
                        BEARER_AUTH,
                        new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT"))
                    .addSecuritySchemes(
                        OAUTH2,
                        new SecurityScheme()
                            .type(SecurityScheme.Type.OAUTH2)
                            .flows(
                                new OAuthFlows()
                                    .authorizationCode(
                                        new OAuthFlow()
                                            .authorizationUrl(authorizationUrl)
                                            .tokenUrl(tokenUrl)))))
            .addServersItem(
                new Server().url("http://localhost:8080/book-thymeleaf/").description(""));
    log.debug("OpenAPI specification built — authorization endpoint: {}", authorizationUrl);
    return openAPI;
  }

  /**
   * Configures the security API group for Swagger documentation.
   *
   * @return a {@link GroupedOpenApi} instance for the security group
   */
  @Bean
  public GroupedOpenApi configureSecurityEndpoints() {

    log.debug("Registering Swagger group for security endpoints under /login and /login/**");
    final GroupedOpenApi api =
        GroupedOpenApi.builder()
            .group("security")
            .pathsToMatch(ApiPathConstants.LOGIN.getValue(), ApiPathConstants.LOGIN_ALL.getValue())
            .build();
    log.debug("Security endpoint group registered successfully");
    return api;
  }
}
