package com.nthn.storevision.repository.impl;

import com.nthn.storevision.model.User;
import com.nthn.storevision.repository.UserRepository;
import com.nthn.storevision.repository.jpa.JpaUserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final JpaUserRepository jpaUserRepository;

  @Override
  public List<User> findAll() {
    return jpaUserRepository.findAll().stream().<User>map(u -> u).toList();
  }

  @Override
  public Optional<User> findById(final Integer id) {
    return jpaUserRepository.findById(id).map(entity -> (User) entity);
  }

  @Override
  public Optional<User> findByEmail(final String email) {
    return jpaUserRepository.findByEmail(email).map(entity -> (User) entity);
  }

  @Override
  public Optional<User> findByUsername(final String username) {
    return jpaUserRepository.findByUsername(username).map(entity -> (User) entity);
  }
}
