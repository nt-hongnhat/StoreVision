/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserFactory.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-02 17:09:29
 */

package com.nthn.storevision.factory.user;

import com.nthn.storevision.model.User;
import java.util.Map;
import org.springframework.lang.NonNull;
import org.springframework.modulith.NamedInterface;

/** Interface for creating {@link User} objects from a map of profile attributes. */
@FunctionalInterface
@NamedInterface
public interface UserFactory {
  /**
   * Creates a {@link User} object from the provided profile attributes.
   *
   * @param profile A map containing user profile attributes.
   * @return A {@link User} object.
   */
  User create(@NonNull Map<String, Object> profile);
}
