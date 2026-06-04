/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: AbstractUpdatable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 16:29:22
 */

package com.nthn.storevision.service.common;

import com.nthn.storevision.entity.mapped.BaseEntity;
import com.nthn.storevision.mapper.EntityMapper;
import com.nthn.storevision.repository.generic.SaveRepository;
import com.nthn.storevision.service.generic.Updatable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract base class that provides a default implementation of the {@link Updatable} interface for
 * updating domain objects in a generic manner.
 *
 * <p>This abstract class leverages dependency injection to manage the persistence and
 * transformation of entity objects. Classes extending this base implementation should specify the
 * entity type, DTO type, and the identifier type as type parameters.
 *
 * @param <T> the type of the Data Transfer Object (DTO) used for updates and responses
 * @param <I> the type of the identifier used to uniquely identify entities
 * @param <E> the type of the entity being updated, extending {@link BaseEntity}
 * @param <J> the type of the repository used for saving entities
 */
@Slf4j
@RequiredArgsConstructor
@Getter
public abstract class AbstractUpdatable<T, I, E extends BaseEntity<I>, J extends SaveRepository<E>>
    implements Updatable<T, I> {

  private final EntityMapper<T, I, E> mapper;
  private final J repository;

  @Override
  public T update(final I id, final T request) {
    log.debug("Updating entity with ID: {}", id);
    final E entity = getById(id);
    mapper.updateEntity(request, entity);
    repository.save(entity);
    final T updatedDto = mapper.toDTO(entity);
    log.debug("Entity updated successfully with ID: {} to {}", id, updatedDto);
    return updatedDto;
  }

  /**
   * Retrieves an entity by its unique identifier.
   *
   * @param id the unique identifier of the entity to be retrieved
   * @return the entity associated with the provided identifier
   */
  protected abstract E getById(I id);
}
