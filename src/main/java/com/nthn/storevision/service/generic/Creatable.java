/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: Creatable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:04:40
 */

package com.nthn.storevision.service.generic;

/**
 * Functional interface for creating and persisting new domain objects.
 *
 * @param <T> the type of the request/response DTO
 */
@FunctionalInterface
public interface Creatable<T> {

  /**
   * Creates and persists a new domain object.
   *
   * @param request the DTO carrying the data for the new object
   * @return the created DTO, including any generated fields such as ID
   */
  T create(T request);
}
