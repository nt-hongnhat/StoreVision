package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for {@link UserEntity}.
 * Provides standard JPA operations and custom query methods for User entities.
 */
@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Integer> {
  /**
   * Finds a user by their email address.
   *
   * @param email The email address of the user.
   * @return An {@link Optional} containing the {@link UserEntity} if found, or empty if not.
   */
  Optional<UserEntity> findByEmail(String email);

  /**
   * Finds a user by their username.
   *
   * @param username The username of the user.
   * @return An {@link Optional} containing the {@link UserEntity} if found, or empty if not.
   */
  Optional<UserEntity> findByUsername(String username);
}
