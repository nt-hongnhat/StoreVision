/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: ReadRepository.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-12 23:23:57
 */

package com.nthn.storevision.repository.generic;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Minimal read contract shared by all domain repository interfaces. {@link
 * com.nthn.storevision.service.common.AbstractReadable} depends on this interface rather than
 * {@code JpaRepository} so that the service layer stays decoupled from Spring Data JPA.
 *
 * @param <E> the entity type
 * @param <I> the identifier type
 */
public interface ReadRepository<E, I> {

  /**
   * Retrieves all entities from the data source.
   *
   * @return a list of all entities of type E
   */
  List<E> findAll();

  /**
   * Retrieves a paginated list of all active entities from the data source.
   *
   * @param pageable the pagination information including page number and size; must not be null
   * @return a {@code Page} containing the active entities of type E
   */
  Page<E> findAllActive(Pageable pageable);

  /**
   * Retrieves an entity by its identifier from the repository.
   *
   * @param id the identifier of the entity to be retrieved
   * @return an {@code Optional} containing the entity if found, or an empty {@code Optional} if not
   *     found
   */
  Optional<E> findById(I id);

  /**
   * Checks whether an entity with the given identifier exists in the data source.
   *
   * @param id the identifier of the entity to check
   * @return true if an entity with the specified identifier exists, false otherwise
   */
  boolean isExistsById(I id);
}
