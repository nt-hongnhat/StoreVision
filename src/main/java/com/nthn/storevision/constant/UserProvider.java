/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: UserProvider.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-13 17:22:53
 */

package com.nthn.storevision.constant;

/** Defines the authentication providers for users in the system. */
public enum UserProvider {
  /**
   * Represents an authentication provider that allows users to log in using their Google accounts.
   * This provider leverages Google's identity services for authentication, enabling secure and
   * convenient user access through external credentials.
   */
  GOOGLE,
  /**
   * Represents a local authentication provider for users within the system. This provider is used
   * to authenticate users directly via the application's database without relying on external
   * identity services.
   */
  LOCAL
}
