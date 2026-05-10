/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ApiResponse.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:08:21
 */

package com.nthn.storevision.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Abstract class representing a standard API response. This class contains timestamps for both
 * local and UTC time, as well as a route for the API response.
 */
@Data
@AllArgsConstructor
public abstract class ApiResponse {

  /**
   * The local timestamp when the response is created. This timestamp is in the local time zone of
   * the server.
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private final ZonedDateTime localTimestamp = ZonedDateTime.now();

  /**
   * The timestamp when the response is created in UTC. This timestamp is standardized to
   * Coordinated Universal Time (UTC).
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private final ZonedDateTime utcTimestamp = ZonedDateTime.now(ZoneId.of("UTC"));

  /**
   * The route associated with the API response. This can be used to indicate the endpoint that
   * generated the response.
   */
  private String route;
}
