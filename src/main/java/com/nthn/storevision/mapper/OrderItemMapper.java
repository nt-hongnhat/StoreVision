/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 20:27:49
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.dto.OrderItemDTO;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.entity.ProductEntity;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

/**
 * Mapper interface for converting between {@link OrderItemEntity} and {@link OrderItemDTO}. This
 * interface uses MapStruct to generate the implementation.
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    config = BaseEntityMapperConfig.class)
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, Long, OrderItemEntity> {

  /**
   * Maps a given order ID to an {@link OrderEntity}. If the provided order ID is {@code null}, this
   * method will return {@code null}. Otherwise, it creates an {@link OrderEntity}, sets the
   * provided ID, and returns the entity.
   *
   * @param orderId The ID of the order to map. Can be {@code null}.
   * @return An {@link OrderEntity} with the provided ID, or {@code null} if the input was {@code
   *     null}.
   */
  default @Nullable OrderEntity mapOrderIdToOrderEntity(final Long orderId) {
    if (null == orderId) {
      return null;
    }
    // In a real application, you would fetch the OrderEntity from the database here
    // For now, we'll create a stub OrderEntity with just the ID
    final OrderEntity order = new OrderEntity();
    order.setId(orderId);
    return order;
  }

  /**
   * Maps a given product ID to a {@link ProductEntity}. If the provided product ID is {@code null},
   * this method will return {@code null}. Otherwise, it creates a {@link ProductEntity}, sets the
   * provided ID, and returns the entity.
   *
   * @param productId The ID of the product to map. Can be {@code null}.
   * @return A {@link ProductEntity} with the provided ID, or {@code null} if the input was {@code
   *     null}.
   */
  default @Nullable ProductEntity mapProductIdToProductEntity(final Long productId) {
    if (null == productId) {
      return null;
    }
    // In a real application, you would fetch the ProductEntity from the database here
    // For now, we'll create a stub ProductEntity with just the ID
    final ProductEntity product = new ProductEntity();
    product.setId(productId);
    return product;
  }

  /**
   * Converts an {@link OrderItemEntity} to an {@link OrderItemDTO}.
   *
   * @param orderItem The OrderItemEntity to convert.
   * @return The converted OrderItemDTO.
   */
  @Mapping(source = "order.id", target = "orderId")
  @Mapping(source = "product.id", target = "productId")
  OrderItemDTO toDTO(OrderItemEntity orderItem);

  /**
   * Converts an {@link OrderItemDTO} to an {@link OrderItemEntity}.
   *
   * @param orderItemDTO The OrderItemDTO to convert.
   * @return The converted OrderItemEntity.
   */
  @Mapping(
      target = "order",
      expression = "java(mapOrderIdToOrderEntity(orderItemDTO.getOrderId()))")
  @Mapping(
      target = "product",
      expression = "java(mapProductIdToProductEntity(orderItemDTO.getProductId()))")
  OrderItemEntity toEntity(OrderItemDTO orderItemDTO);

  /**
   * Updates an existing {@link OrderItemEntity} with values from the given {@link OrderItemDTO}.
   * The {@code id}, {@code order}, and {@code product} fields are ignored — order and product
   * associations must be changed via dedicated workflows, not a regular update.
   *
   * @param dto the source DTO carrying updated field values
   * @param entity the target entity to be updated in place
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "order", ignore = true)
  @Mapping(target = "product", ignore = true)
  void updateEntity(OrderItemDTO dto, @MappingTarget OrderItemEntity entity);
}
