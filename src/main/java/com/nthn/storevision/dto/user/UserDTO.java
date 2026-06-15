/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: UserDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 22:42:01
 */

package com.nthn.storevision.dto.user;

import com.nthn.storevision.dto.response.HasIdentifier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Data Transfer Object for {@link com.nthn.storevision.model.User}. Used to transfer user data
 * between layers, often for API requests and responses.
 */
@Schema(description = "Application user account, used for authentication and authorization.")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDTO implements HasIdentifier<Long> {

  @Schema(description = "User's email address.", example = "user@example.com")
  @NotBlank(message = "{user.email.required}")
  @Email(message = "{user.email.invalid}")
  @Size(max = 100, message = "{user.email.size}")
  private String email;

  @Schema(description = "User's first name.", example = "Nguyen")
  @NotBlank(message = "{user.firstName.required}")
  @Size(max = 45, message = "{user.firstName.size}")
  private String firstName;

  @Schema(
      description = "Auto-assigned primary key.",
      example = "1",
      accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @Schema(description = "User's last name.", example = "Van A")
  @NotBlank(message = "{user.lastName.required}")
  @Size(max = 45, message = "{user.lastName.size}")
  private String lastName;

  @Schema(description = "User's unique login username.", example = "nguyenvana")
  @NotBlank(message = "{user.username.required}")
  @Size(max = 45, message = "{user.username.size}")
  private String username;
}
