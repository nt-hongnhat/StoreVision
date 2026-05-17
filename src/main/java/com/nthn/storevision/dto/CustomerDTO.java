/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomerDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:10:30
 */

package com.nthn.storevision.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for Customer.
 * Used to transfer customer data between layers, often for API requests and responses.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
    private Long id;

    @NotBlank(message = "{customer.name.required}")
    private String name;

    @NotBlank(message = "{customer.email.required}")
    @Email(message = "{customer.email.invalid}")
    private String email;

    private String phone;
    private String address;
}
