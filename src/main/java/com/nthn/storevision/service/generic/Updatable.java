/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: Updatable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 13:58:22
 */

package com.nthn.storevision.service.generic;

/**
 * Functional interface for updating domain objects by ID.
 *
 * @param <T> the type of the request/response DTO
 */
@FunctionalInterface
public interface Updatable<T> {

  /**
   * Updates an existing domain object identified by the given ID.
   *
   * @param id the identifier of the object to update
   * @param request the DTO carrying the new field values
   * @return the updated DTO
   */
  T update(Long id, T request);
}
