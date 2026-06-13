package com.nthn.storevision;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/** Main entry point for the StoreVision Spring Boot application. */
@Slf4j
@SpringBootApplication
@EnableScheduling
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
