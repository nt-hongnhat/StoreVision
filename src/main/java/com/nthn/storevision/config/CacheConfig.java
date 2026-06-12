/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CacheConfig.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 22:20:12
 */

package com.nthn.storevision.config;


import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Enables Spring's annotation-driven caching for the analytics module (BR-A03).
 *
 * <p>Backed by an in-memory {@link ConcurrentMapCacheManager} for now. BR-A03 targets Redis with a
 * 5-minute TTL — switching only requires adding {@code spring-boot-starter-data-redis}, replacing
 * this bean with the Redis cache manager (or {@code spring.cache.type=redis} plus {@code
 * spring.cache.redis.time-to-live=5m}); the {@code @Cacheable} annotations stay unchanged.
 */
@Configuration
@EnableCaching
public class CacheConfig {

  /**
   * Provides the in-memory cache manager backing the analytics {@code @Cacheable} methods.
   *
   * @return a {@link CacheManager} that creates caches on demand; never {@code null}
   */
  @Bean
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager();
  }
}
