package com.nthn.storevision.entity;

import com.nthn.storevision.entity.mapped.BaseEntity;
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

/**
 * Represents a user entity in the database.
 * This class maps to the "users" table, implements the {@link User} interface,
 * and extends {@link BaseEntity} for auditing purposes.
 */
@Getter
@Setter
@Entity
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Table(name = "users")
public class UserEntity extends BaseEntity<Integer> implements User {

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

  /**
   * Returns the unique identifier of the user.
   *
   * @return The user's ID.
   */
  @Override
  public Integer getId() {
    return id;
  }

  /**
   * Computes the hash code for this UserEntity.
   *
   * @return The hash code.
   */
  @Override
  public final int hashCode() {
    return this instanceof final HibernateProxy hibernateProxy ?
        hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
  }

  /**
   * Compares this UserEntity to the specified object. The result is {@code true} if and only if
   * the argument is not {@code null} and is a UserEntity object that has the same id field value.
   *
   * @param o The object to compare this UserEntity against.
   * @return {@code true} if the given object represents a UserEntity equivalent to this UserEntity,
   *         {@code false} otherwise.
   */
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

  /**
   * Sets the password for this user, ensuring it is hashed.
   *
   * @param password The raw password to set.
   * @return This UserEntity instance for chaining.
   */
  @Override
  public User setPassword(@NonNull final String password) {
    this.password = PasswordUtil.ensurePasswordIsHashed(password);
    return this;
  }
}
