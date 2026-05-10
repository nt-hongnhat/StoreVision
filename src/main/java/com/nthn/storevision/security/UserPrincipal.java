package com.nthn.storevision.security;

import com.nthn.storevision.model.User;
import com.nthn.storevision.util.PasswordUtil;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
  private final Collection<? extends GrantedAuthority> authorities;
  private final User user;

  @Override
  public String getPassword() {
    return PasswordUtil.ensurePasswordIsHashed(user.getPassword());
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }
}
