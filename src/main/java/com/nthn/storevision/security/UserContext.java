package com.nthn.storevision.security;

import java.util.Map;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for managing user attributes within a ThreadLocal context.
 * This allows user-specific data to be accessible throughout the request scope.
 */
@UtilityClass
@Slf4j
public class UserContext {

  private static final ThreadLocal<Map<String, Object>> userAttributes = new ThreadLocal<>();

  /**
   * Retrieves the user attributes stored in the current thread's context.
   *
   * @return A map of user attributes, or {@code null} if not set.
   */
  public Map<String, Object> getUserAttributes() {
    log.debug("getUserAttributes: userContext={}", userAttributes.get());
    return userAttributes.get();
  }

  /**
   * Sets the user attributes for the current thread's context.
   *
   * @param userContext A map containing user attributes to set.
   */
  public void setUserAttributes(Map<String, Object> userContext) {
    log.debug("setUserAttributes: userContext={}", userContext);
    userAttributes.set(userContext);
  }

  /**
   * Resets (removes) the user attributes from the current thread's context.
   */
  public void resetUserAttributes() {
    log.debug("resetUserAttributes: userAttributes={}", userAttributes.get());
    userAttributes.remove();
  }
}
