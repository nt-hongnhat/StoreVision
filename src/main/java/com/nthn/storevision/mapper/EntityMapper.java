/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EntityMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 17:01:35
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.entity.mapped.BaseEntity;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Generic bidirectional mapper contract shared by all domain MapStruct mappers.
 *
 * <p>Abstracts the two core conversion directions so that service abstractions (e.g. {@link
 * com.nthn.storevision.service.common.AbstractCreatable}) can depend on this interface rather than
 * on concrete mapper types.
 *
 * @param <T> the DTO type
 * @param <I> the ID type
 * @param <E> the JPA entity type
 */
@MapperConfig(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EntityMapper<T, I, E extends BaseEntity<I>> {

  /**
   * Converts a JPA entity to its corresponding DTO.
   *
   * @param entity the entity to convert; must not be {@code null}
   * @return the resulting DTO
   */
  T toDTO(E entity);

  /**
   * Converts a DTO to its corresponding JPA entity.
   *
   * @param dto the DTO to convert; must not be {@code null}
   * @return the resulting entity
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "lastModifiedBy", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  @Mapping(target = "deletedBy", ignore = true)
  @Mapping(target = "deletedDate", ignore = true)
  E toEntity(T dto);

  /**
   * Updates an existing entity with values from the provided DTO. The {@code id} field is ignored
   * during the update process.
   *
   * @param dto The source Data Transfer Object (DTO) containing updated values.
   * @param entity The target entity to be updated; should not be null.
   */
  @Mapping(target = "id", ignore = true)
  void updateEntity(T dto, @MappingTarget E entity);
}
