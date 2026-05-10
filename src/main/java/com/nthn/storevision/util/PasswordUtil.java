package com.nthn.storevision.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PasswordUtil {

  private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

  @NonNull
  public static String ensurePasswordIsHashed(final String password) {
    return PasswordUtil.isHashedPassword(password) ? password : PasswordUtil.hashPassword(password);
  }

  @NonNull
  public static String hashPassword(String plainPassword) {
    return PASSWORD_ENCODER.encode(plainPassword);
  }

  public static boolean isHashedPassword(String password) {
    return password != null && password.matches("^\\$2[aby]\\$\\d{2}\\$[./A-Za-z0-9]{53}$");
  }

  public static boolean verifyPassword(String plainPassword, String hashedPassword) {
    return PASSWORD_ENCODER.matches(plainPassword, hashedPassword);
  }
}
