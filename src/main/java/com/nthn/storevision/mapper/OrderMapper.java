/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: OrderMapper.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:15:54
 */

package com.nthn.storevision.mapper;

import com.nthn.storevision.dto.OrderDTO;
import com.nthn.storevision.entity.CustomerEntity;
import com.nthn.storevision.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper interface for converting between {@link OrderEntity} and {@link OrderDTO}.
 * This interface uses MapStruct to generate the implementation.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
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
    @Mapping(target = "customer", expression = "java(mapCustomerIdToCustomerEntity(orderDTO.getCustomerId()))")
    OrderEntity toEntity(OrderDTO orderDTO);

    // Helper method to map customerId to CustomerEntity
    default CustomerEntity mapCustomerIdToCustomerEntity(Long customerId) {
        if (customerId == null) {
            return null;
        }
        // In a real application, you would fetch the CustomerEntity from the database here
        // For now, we'll create a stub CustomerEntity with just the ID
        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);
        return customer;
    }
}
