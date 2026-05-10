package com.nthn.storevision.factory.user;

import com.nthn.storevision.model.User;
import java.util.Map;
import org.springframework.lang.NonNull;

/** Interface for creating {@link User} objects from a map of profile attributes. */
@FunctionalInterface
public interface UserFactory {
  /**
   * Creates a {@link User} object from the provided profile attributes.
   *
   * @param profile A map containing user profile attributes.
   * @return A {@link User} object.
   */
  User create(@NonNull Map<String, Object> profile);
}
