package com.nthn.storevision.repository;

import com.nthn.storevision.model.User;
import java.util.Optional;

public interface UserRepository {
  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);
}
