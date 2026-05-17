/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-05-17 14:25:05
 */

package com.nthn.storevision.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for Employee.
 * Used to transfer employee data between layers, often for API requests and responses.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "{employee.username.required}")
    private String username;

    @NotBlank(message = "{employee.password.required}")
    private String password; // Note: In a real app, password should not be returned in DTO for security.

    @NotBlank(message = "{employee.role.required}")
    private String role;
}
