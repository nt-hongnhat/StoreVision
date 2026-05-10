package com.nthn.storevision.security;

import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.repository.jpa.JpaUserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final JpaUserEntityRepository jpaRepository;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final UserEntity user = jpaRepository.findByUsername(username)
            .or(() -> jpaRepository.findByEmail(username))
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + username));
    return new UserPrincipal(Collections.emptyList(), user);
  }
}
