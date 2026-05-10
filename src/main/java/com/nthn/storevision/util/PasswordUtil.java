package com.nthn.storevision.util;

import lombok.experimental.UtilityClass;
import org.jspecify.annotations.Nullable;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

/**
 * Utility class for password operations, including hashing and verification. Uses BCrypt for
 * password encoding.
 */
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
  @NonNull
  public String ensurePasswordIsHashed(final String password) {
    return isHashedPassword(password) ? password : hashPassword(password);
  }

  /**
   * Verifies a plain text password against a hashed password.
   *
   * @param plainPassword The plain text password.
   * @param hashedPassword The hashed password to compare against.
   * @return {@code true} if the plain password matches the hashed password, {@code false}
   *     otherwise.
   */
  public boolean verifyPassword(final String plainPassword, final String hashedPassword) {
    return PASSWORD_ENCODER.matches(plainPassword, hashedPassword);
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
