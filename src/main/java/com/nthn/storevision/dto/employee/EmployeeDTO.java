/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: EmployeeDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 22:45:30
 */

package com.nthn.storevision.dto.employee;

import com.nthn.storevision.constant.employee.EmployeeRole;
import com.nthn.storevision.dto.response.HasIdentifier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Data Transfer Object for Employee. Used to transfer employee data between layers, often for API
 * requests and responses.
 */
@Schema(description = "Employee account, including login credentials and assigned permission role.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EmployeeDTO implements HasIdentifier<Long> {

  @Schema(description = "Employee's email address.", example = "employee@example.com")
  @NotBlank(message = "{employee.email.required}")
  @Email(message = "{employee.email.invalid}")
  private String email;

  @Schema(
      description = "Auto-assigned primary key.",
      example = "1",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @Schema(description = "Employee's full name.", example = "Tran Thi B")
  @NotBlank(message = "{employee.name.required}")
  private String name;

  @Schema(
      description = "Employee's login password.",
      example = "P@ssw0rd123",
      accessMode = Schema.AccessMode.WRITE_ONLY)
  @NotBlank(message = "{employee.password.required}")
  private String password;

  @Schema(description = "Permission role assigned to the employee.", example = "STAFF")
  @NotNull(message = "{employee.role.required}")
  private EmployeeRole role;

  @Schema(description = "Employee's unique login username.", example = "tranthib")
  @NotBlank(message = "{employee.username.required}")
  private String username;
}
