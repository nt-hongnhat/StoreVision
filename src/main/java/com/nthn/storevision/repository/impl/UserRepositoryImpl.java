package com.nthn.storevision.repository.impl;

import com.nthn.storevision.mapper.UserMapper;
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
  private final UserMapper userMapper;

  @Override
  public Optional<User> findByEmail(final String email) {
    return jpaUserRepository.findByEmail(email).map(userMapper::toModel);
  }

  @Override
  public Optional<User> findByUsername(final String username) {
    return jpaUserRepository.findByUsername(username).map(userMapper::toModel);
  }
}
