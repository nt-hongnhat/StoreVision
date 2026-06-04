/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: PasswordUtil.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 17:19:31
 */

package com.nthn.storevision.util;

import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class for password operations, including hashing and verification. Uses BCrypt for
 * password encoding.
 */
@Slf4j
@UtilityClass
public class PasswordUtil {

  private final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
  private final Pattern PASSWORD_HASH_REGEX =
      Pattern.compile("^\\$2[aby]\\$\\d{2}\\$[./A-Za-z0-9]{53}$");

  /**
   * Ensures that the given password is hashed. If it's already hashed, it returns the password as
   * is. Otherwise, it hashes the password using BCrypt.
   *
   * @param password The password to check and hash if necessary.
   * @return The hashed password.
   */
  public @Nullable String ensurePasswordIsHashed(final String password) {
    log.debug("Ensuring password is hashed");
    return isHashedPassword(password) ? password : hashPassword(password);
  }

  /**
   * Hashes a plain text password using BCrypt.
   *
   * @param plainPassword The plain text password to hash.
   * @return The BCrypt hashed password.
   */
  @Nullable
  private String hashPassword(final String plainPassword) {
    return PASSWORD_ENCODER.encode(plainPassword);
  }

  /**
   * Checks if a given string is a BCrypt hashed password.
   *
   * @param password The string to check.
   * @return {@code true} if the string appears to be a BCrypt hashed password, {@code false}
   *     otherwise.
   */
  private boolean isHashedPassword(final String password) {
    return null != password && PASSWORD_HASH_REGEX.matcher(password).matches();
  }
}
