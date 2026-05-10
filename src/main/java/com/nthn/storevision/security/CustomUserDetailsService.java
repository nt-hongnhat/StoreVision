package com.nthn.storevision.security;

import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.repository.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Custom implementation of Spring Security's {@link UserDetailsService}.
 * This service is responsible for loading user-specific data during authentication.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final JpaUserRepository jpaRepository;

  /**
   * Locates the user based on the username. In this implementation, it tries to find
   * a user by username first, and if not found, then by email.
   *
   * @param username The username (or email) identifying the user whose data is required.
   * @return A {@link UserDetails} object (specifically {@link UserPrincipal}) containing the user's information.
   * @throws UsernameNotFoundException If the user could not be found or has no granted authorities.
   */
  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final UserEntity user = jpaRepository.findByUsername(username)
            .or(() -> jpaRepository.findByEmail(username))
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + username));
    return new UserPrincipal(Collections.emptyList(), user);
  }
}
