/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderItemMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:21:39
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.dto.OrderItemDTO;
import com.nthn.storevision.entity.OrderEntity;
import com.nthn.storevision.entity.OrderItemEntity;
import com.nthn.storevision.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper interface for converting between {@link OrderItemEntity} and {@link OrderItemDTO}.
 * This interface uses MapStruct to generate the implementation.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemMapper {
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
    @Mapping(target = "order", expression = "java(mapOrderIdToOrderEntity(orderItemDTO.getOrderId()))")
    @Mapping(target = "product", expression = "java(mapProductIdToProductEntity(orderItemDTO.getProductId()))")
    OrderItemEntity toEntity(OrderItemDTO orderItemDTO);

    // Helper method to map orderId to OrderEntity
    default OrderEntity mapOrderIdToOrderEntity(Long orderId) {
        if (orderId == null) {
            return null;
        }
        // In a real application, you would fetch the OrderEntity from the database here
        // For now, we'll create a stub OrderEntity with just the ID
        OrderEntity order = new OrderEntity();
        order.setId(orderId);
        return order;
    }

    // Helper method to map productId to ProductEntity
    default ProductEntity mapProductIdToProductEntity(Long productId) {
        if (productId == null) {
            return null;
        }
        // In a real application, you would fetch the ProductEntity from the database here
        // For now, we'll create a stub ProductEntity with just the ID
        ProductEntity product = new ProductEntity();
        product.setId(productId);
        return product;
    }
}
