package com.nthn.storevision.factory.user.impl;

import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.factory.user.UserFactory;
import com.nthn.storevision.model.User;
import java.util.Map;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

/**
 * A factory for creating {@link User} objects from Google authentication profiles.
 * This class implements the {@link UserFactory} interface.
 */
@Component
public class GoogleAuthUserFactory implements UserFactory {

  private static final String EMAIL = "email";
  private static final String FAMILY_NAME = "family_name";
  private static final String GIVEN_NAME = "given_name";
  private static final String SUB = "sub";

  /**
   * Creates a {@link User} object from the provided Google profile attributes.
   * The profile map is expected to contain "email", "given_name", "family_name", and "sub" keys.
   *
   * @param profile A map containing user profile attributes from Google.
   * @return A {@link User} object populated with data from the Google profile.
   */
  @Override
  public User create(@NonNull final Map<String, Object> profile) {
    final String email = profile.get(EMAIL).toString();
    final UserEntity user = new UserEntity();
    user.setUsername(email)
        .setEmail(email)
        .setFirstName(profile.get(GIVEN_NAME).toString())
        .setLastName(profile.get(FAMILY_NAME).toString())
        .setPassword(profile.get(SUB).toString());
    return user;
  }
}
