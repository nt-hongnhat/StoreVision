package com.nthn.storevision.repository.jpa;

import com.nthn.storevision.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserEntityRepository extends JpaRepository<UserEntity, Integer> {
  Optional<UserEntity> findByEmail(String email);

  Optional<UserEntity> findByUsername(String username);
}
