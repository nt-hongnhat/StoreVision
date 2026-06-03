package com.nthn.storevision.repository.impl;

import com.nthn.storevision.model.User;
import com.nthn.storevision.repository.UserRepository;
import com.nthn.storevision.repository.jpa.JpaUserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final JpaUserRepository jpaUserRepository;

  @Override
  public Optional<User> findByEmail(final String email) {
    return jpaUserRepository.findByEmail(email).map(entity -> (User) entity);
  }

  @Override
  public Optional<User> findByUsername(final String username) {
    return jpaUserRepository.findByUsername(username).map(entity -> (User) entity);
  }
}
