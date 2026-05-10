package com.nthn.storevision.model;

public interface User {
  String getEmail();

  User setEmail(String email);

  String getFirstName();

  User setFirstName(String firstName);

  String getLastName();

  User setLastName(String lastName);

  String getPassword();

  User setPassword(String password);

  String getUsername();

  User setUsername(String username);
}
