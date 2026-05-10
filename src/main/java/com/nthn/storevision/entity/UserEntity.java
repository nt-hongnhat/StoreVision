package com.nthn.storevision.entity;

import com.nthn.storevision.model.User;
import com.nthn.storevision.util.PasswordUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Table(name = "users")
public class UserEntity implements User {

  @Size(max = 100)
  @NotNull
  @Column(name = "email", nullable = false, unique = true, length = 100)
  private String email;
  @Size(max = 45)
  @NotNull
  @Column(name = "first_name", nullable = false, length = 45)
  private String firstName;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;
  @Size(max = 45)
  @NotNull
  @Column(name = "last_name", nullable = false, length = 45)
  private String lastName;

  @Size(max = 255)
  @NotNull
  @Column(name = "password", nullable = false)
  private String password;

  @Size(max = 45)
  @NotNull
  @Column(name = "username", nullable = false, unique = true, length = 45)
  private String username;

  @Override
  public final int hashCode() {
    return this instanceof final HibernateProxy hibernateProxy ?
        hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }

  @Override
  public final boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (null == o) {
      return false;
    }
    final Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
    final Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    final UserEntity that = (UserEntity) o;
    return null != id && Objects.equals(id, that.id);
  }

  @Override
  public User setPassword(@NonNull final String password) {
    this.password = PasswordUtil.ensurePasswordIsHashed(password);
    return this;
  }
}
