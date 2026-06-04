/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: JpaConfig.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 10:30:28
 */

package com.nthn.storevision.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class for JPA-related settings. Enables JPA auditing for automatically populating
 * audit fields like createdDate and lastModifiedDate.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "userAuditorAware")
public class JpaConfig {}
