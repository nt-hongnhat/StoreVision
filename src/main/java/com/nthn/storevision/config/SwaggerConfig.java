/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: SwaggerConfig.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 17:12:44
 */

package com.nthn.storevision.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
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

  private static final String API_TITLE = "StoreVision API";
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
                new Server()
                    .url("http://localhost:8080/store-vision/api/v1")
                    .description("Local Development — v1"));
    log.debug("OpenAPI specification built — authorization endpoint: {}", authorizationUrl);
    return openAPI;
  }
}
