package com.nthn.storevision.repository;

import com.nthn.storevision.model.User;
import java.util.Optional;

public interface UserRepository extends ReadRepository<User, Integer> {

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);
}
