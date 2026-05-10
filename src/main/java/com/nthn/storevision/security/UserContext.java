package com.nthn.storevision.security;

import java.util.Map;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class UserContext {

  private static final ThreadLocal<Map<String, Object>> userAttributes = new ThreadLocal<>();

  public Map<String, Object> getUserAttributes() {
    log.debug("getUserAttributes: userContext={}", userAttributes.get());
    return userAttributes.get();
  }

  public void setUserAttributes(Map<String, Object> userContext) {
    log.debug("setUserAttributes: userContext={}", userContext);
    userAttributes.set(userContext);
  }

  public void resetUserAttributes() {
    log.debug("resetUserAttributes: userAttributes={}", userAttributes.get());
    userAttributes.remove();
  }
}
