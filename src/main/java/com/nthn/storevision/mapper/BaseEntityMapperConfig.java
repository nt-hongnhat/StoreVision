/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: BaseEntityMapperConfig.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 16:08:05
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.model.User;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Shared MapStruct configuration for all entity mappers.
 *
 * <p>Two concerns are handled here:
 *
 * <ol>
 *   <li><b>Audit-field ignore prototype (UserEntity only)</b> — {@link User} extends {@link
 *       com.nthn.storevision.model.CustomAuditable}, which exposes {@code Optional<User>} getters
 *       for {@code createdBy}, {@code lastModifiedBy}, and {@code deletedBy}. MapStruct cannot
 *       automatically unwrap those to {@code UserEntity}, so explicit {@code ignore = true} mappings
 *       are required. DTO-based mappers (Customer, Employee, Product, Order, OrderItem) do not have
 *       these getters on their source, so no prototype is needed for them.
 *   <li><b>Unmapped-target policy</b> — set to {@code IGNORE} so that audit fields present on all
 *       {@code BaseEntity} subclasses (managed by {@code CustomAuditingEntityListener}) do not
 *       produce "unmapped target property" warnings when mapping from plain DTOs.
 * </ol>
 */
@FunctionalInterface
@MapperConfig(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BaseEntityMapperConfig {

  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "createdDate", ignore = true)
  @Mapping(target = "lastModifiedBy", ignore = true)
  @Mapping(target = "lastModifiedDate", ignore = true)
  @Mapping(target = "deletedBy", ignore = true)
  @Mapping(target = "deletedDate", ignore = true)
  @Mapping(target = "deleted", ignore = true)
  UserEntity toEntity(User source);
}
