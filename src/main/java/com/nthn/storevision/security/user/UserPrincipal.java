/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserPrincipal.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 17:01:42
 */

package com.nthn.storevision.security.user;

import com.nthn.storevision.model.User;
import com.nthn.storevision.util.PasswordUtil;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.modulith.NamedInterface;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Custom implementation of {@link UserDetails} to represent an authenticated user.
 * This class wraps a {@link User} object and provides Spring Security with user details.
 */
@NamedInterface
@Getter
@Setter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
  private final Collection<? extends GrantedAuthority> authorities;
  private final User user;

  /**
   * Returns the password used to authenticate the user.
   * Ensures the password is hashed before returning.
   *
   * @return The hashed password.
   */
  @Override
  public String getPassword() {
    return PasswordUtil.ensurePasswordIsHashed(user.getPassword());
  }

  /**
   * Returns the username used to authenticate the user.
   *
   * @return The username.
   */
  @Override
  public String getUsername() {
    return user.getUsername();
  }

  /**
   * Indicates whether the user's account has expired.
   *
   * @return {@code true} if the user's account is valid (non-expired), {@code false} otherwise.
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * Indicates whether the user is locked or unlocked.
   *
   * @return {@code true} if the user is not locked, {@code false} otherwise.
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * Indicates whether the user's credentials (password) has expired.
   *
   * @return {@code true} if the user's credentials are valid (non-expired), {@code false} otherwise.
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * Indicates whether the user is enabled or disabled.
   *
   * @return {@code true} if the user is enabled, {@code false} otherwise.
   */
  @Override
  public boolean isEnabled() {
    return true;
  }
}
