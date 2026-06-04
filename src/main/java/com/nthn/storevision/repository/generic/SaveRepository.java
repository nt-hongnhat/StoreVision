/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: SaveRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 14:31:23
 */

package com.nthn.storevision.repository.generic;

/**
 * Minimal write contract shared by all domain repository interfaces. {@link
 * com.nthn.storevision.service.common.AbstractCreatable} depends on this interface rather than
 * {@code JpaRepository} so that the service layer stays decoupled from Spring Data JPA.
 *
 * @param <E> the entity type
 */
@FunctionalInterface
public interface SaveRepository<E> {

  /**
   * Saves the given entity to the repository.
   *
   * @param entity the entity to be saved; must not be null
   * @return the saved entity
   */
  E save(E entity);
}
