/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserDTO.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 16:07:29
 */

package com.nthn.storevision.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for {@link com.nthn.storevision.model.User}. Used to transfer user data
 * between layers, often for API requests and responses.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

  @NotBlank(message = "{user.email.required}")
  @Email(message = "{user.email.invalid}")
  @Size(max = 100, message = "{user.email.size}")
  private String email;

  @NotBlank(message = "{user.firstName.required}")
  @Size(max = 45, message = "{user.firstName.size}")
  private String firstName;

  private Integer id;

  @NotBlank(message = "{user.lastName.required}")
  @Size(max = 45, message = "{user.lastName.size}")
  private String lastName;

  @NotBlank(message = "{user.username.required}")
  @Size(max = 45, message = "{user.username.size}")
  private String username;
}
