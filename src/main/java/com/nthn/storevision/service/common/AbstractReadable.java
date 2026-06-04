/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: AbstractReadable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:18:27
 */

package com.nthn.storevision.service.common;

import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.repository.generic.ReadRepository;
import com.nthn.storevision.service.generic.Readable;
import java.util.List;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;

/**
 * Abstract class providing common capability for reading entities.
 *
 * <p>The repository bound uses {@link ReadRepository} rather than {@code JpaRepository} so that the
 * service layer stays decoupled from Spring Data JPA.
 *
 * @param <T> the type of the entity
 * @param <I> the type of the entity's identifier
 * @param <J> the domain repository type, must extend {@link ReadRepository}
 */
@Slf4j
@RequiredArgsConstructor
@Getter
public abstract class AbstractReadable<T, I, J extends ReadRepository<? extends T, I>>
    implements Readable<T, I> {

  private final J repository;

  /**
   * Retrieves all entities and maps them to a different type using the provided mapper function.
   *
   * @param mapper a function to map entities of type T to type R
   * @param <R> the type of the mapped result
   * @return a list of mapped entities
   */
  @Override
  public <R> List<R> findAll(final Function<? super T, R> mapper) {
    log.debug("Fetching all entities");
    return repository.findAll().stream().map(mapper).toList();
  }

  /**
   * Retrieves an entity by its identifier and maps it to a different type using the provided mapper
   * function.
   *
   * @param id the identifier of the entity to be retrieved
   * @param mapper a function to map the entity of type T to type R
   * @param <R> the type of the mapped result
   * @return the mapped entity
   * @throws com.nthn.storevision.exception.EntityNotFoundException if the entity isn't found
   */
  @Override
  public <R> R findById(final I id, @NonNull final Function<? super T, R> mapper) {
    log.debug("Fetching entity by id: {}", id);
    return mapper.apply(
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException(getNotFoundMessageCode(), id)));
  }

  /**
   * Retrieves the message code to be used when a resource isn't found.
   *
   * @return the message code for a not found resource
   */
  protected abstract String getNotFoundMessageCode();
}
