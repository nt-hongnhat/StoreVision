/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 17:15:08
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.entity.OrderEntity;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

/**
 * Mapper interface for converting between {@link OrderEntity} and {@link OrderDTO}. This interface
 * uses MapStruct to generate the implementation.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, config = EntityMapperConfig.class)
public interface OrderMapper extends EntityMapperConfig<OrderDTO, Long, OrderEntity> {
  /**
   * Maps a given customer ID to a {@link CustomerEntity}. If the provided customer ID is {@code
   * null}, this method will return {@code null}. Otherwise, it creates a {@link CustomerEntity},
   * sets the provided ID, and returns the entity.
   *
   * @param customerId The ID of the customer to map. Can be {@code null}.
   * @return A {@link CustomerEntity} with the provided ID, or {@code null} if the input was {@code
   *     null}.
   */
  default @Nullable CustomerEntity mapCustomerIdToCustomerEntity(final Long customerId) {
    if (null == customerId) {
      return null;
    }
    // In a real application, you would fetch the CustomerEntity from the database here
    // For now, we'll create a stub CustomerEntity with just the ID
    final CustomerEntity customer = new CustomerEntity();
    customer.setId(customerId);
    return customer;
  }

  /**
   * Converts an {@link OrderEntity} to an {@link OrderDTO}.
   *
   * @param order The OrderEntity to convert.
   * @return The converted OrderDTO.
   */
  @Mapping(source = "customer.id", target = "customerId")
  OrderDTO toDTO(OrderEntity order);

  /**
   * Converts an {@link OrderDTO} to an {@link OrderEntity}.
   *
   * @param orderDTO The OrderDTO to convert.
   * @return The converted OrderEntity.
   */
  @Mapping(
      target = "customer",
      expression = "java(mapCustomerIdToCustomerEntity(orderDTO.getCustomerId()))")
  OrderEntity toEntity(OrderDTO orderDTO);

  /**
   * Updates an existing {@link OrderEntity} with values from the given {@link OrderDTO}. The {@code
   * id} and {@code customer} fields are ignored — {@code customer} association must be changed via
   * a dedicated workflow, not a regular update.
   *
   * @param dto the source DTO carrying updated field values
   * @param entity the target entity to be updated in place
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "customer", ignore = true)
  void updateEntity(OrderDTO dto, @MappingTarget OrderEntity entity);
}
