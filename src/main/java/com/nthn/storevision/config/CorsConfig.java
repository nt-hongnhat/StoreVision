package com.nthn.storevision.config;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class that sets up Cross-Origin Resource Sharing (CORS)
 * settings for the web application. Implements the {@code WebMvcConfigurer}
 * interface to customize CORS mappings.
 * <p>
 * This configuration restricts access to specific origins, allows specific
 * HTTP methods, and enables credential support to ensure secure and
 * controlled interactions between the backend and frontend.
 */
@Slf4j
@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(final @NonNull CorsRegistry registry) {
    log.debug("Configuring CORS");
    registry.addMapping("/api/**").allowedOrigins("http://localhost:3000") // React frontend default
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*").allowCredentials(true);
    log.debug("CORS configuration completed: {}", registry);
  }
}
