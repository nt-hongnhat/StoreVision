/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: AbstractReadable.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-10 13:06:07
 */

package com.nthn.storevision.service.common;

import com.nthn.storevision.exception.EntityNotFoundException;
import com.nthn.storevision.service.generic.Readable;
import java.util.List;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

/**
 * Abstract class providing common capability for reading entities.
 *
 * @param <T> the type of the entity
 * @param <I> the type of the entity's identifier
 * @param <J> the type of the JPA repository for the entity
 */
@RequiredArgsConstructor
@Getter
public abstract class AbstractReadable<T, I, J extends JpaRepository<? extends T, I>>
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
  public <R> List<R> findAll(final Function<T, R> mapper) {
    return getRepository().findAll().stream().map(mapper).toList();
  }

  /**
   * Retrieves an entity by its identifier and maps it to a different type using the provided mapper
   * function.
   *
   * @param id the identifier of the entity to be retrieved
   * @param mapper a function to map the entity of type T to type R
   * @param <R> the type of the mapped result
   * @return the mapped entity
   * @throws com.nthn.bookthymeleaf.exception.EntityNotFoundException if the entity isn't found
   */
  @Override
  public <R> R findById(final I id, @NonNull final Function<T, R> mapper) {
    return mapper.apply(
        getRepository()
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
