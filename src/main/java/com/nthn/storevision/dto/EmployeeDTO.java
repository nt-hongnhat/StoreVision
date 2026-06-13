/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: EmployeeDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 17:09:35
 */

package com.nthn.storevision.dto;

import com.nthn.storevision.constant.EmployeeRole;
import jakarta.validation.constraints.Email; 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for Employee. Used to transfer employee data between layers, often for API
 * requests and responses.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
  @NotBlank(message = "{employee.email.required}")
  @Email(message = "{employee.email.invalid}")
  private String email;

  private Long id;

  @NotBlank(message = "{employee.name.required}")
  private String name;

  @NotBlank(message = "{employee.password.required}")
  private String password;

  @NotNull(message = "{employee.role.required}")
  private EmployeeRole role;

  @NotBlank(message = "{employee.username.required}")
  private String username;
}
