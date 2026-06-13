/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: StoreVisionApplication.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 16:01:55
 */

package com.nthn.storevision;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/** Main entry point for the StoreVision Spring Boot application. */
@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class StoreVisionApplication {

  /**
   * Main method to start the StoreVision application.
   *
   * @param args Command line arguments.
   */
  static void main(final String[] args) {
    log.info("Starting StoreVision application...");
    SpringApplication.run(StoreVisionApplication.class, args);
    log.info("StoreVision application started successfully!");
  }
}
