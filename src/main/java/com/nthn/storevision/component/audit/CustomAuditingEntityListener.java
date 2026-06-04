/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: CustomAuditingEntityListener.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-04 10:27:38
 */

package com.nthn.storevision.component.audit;

import com.nthn.storevision.annotation.DeletedBy;
import com.nthn.storevision.annotation.DeletedDate;
import com.nthn.storevision.entity.mapped.BaseEntity;
import com.nthn.storevision.exception.EntityAccessException;
import com.nthn.storevision.model.User;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

/**
 * The {@code CustomAuditingEntityListener} class extends the {@link AuditingEntityListener} to
 * provide custom auditing behavior for entities.
 *
 * <p>This listener automatically sets the deletion date, and the user who deleted the entity when
 * an entity marked as deleted is updated.
 *
 * <p>It uses reflection to inspect the fields of the entity for the presence of {@link DeletedDate}
 * and {@link com.nthn.storevision.annotation.DeletedBy} annotations and populates them accordingly.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuditingEntityListener {

  /**
   * A constant representing the field name "createdBy" in an entity. It is typically used in
   * auditing contexts to denote the user responsible for creating the entity. This field is
   * expected to store information about the creator of the entity, such as a username or user ID.
   *
   * <p>This constant facilitates consistency and reduces the likelihood of hardcoding errors when
   * referring to the "createdBy" field across the codebase.
   */
  private static final String CREATED_BY = "createdBy";

  /**
   * Represents the field name for the creation date in entities.
   *
   * <p>This constant is used in auditing processes to identify and manage the creation timestamp of
   * an entity. It denotes the property used to track when the entity was initially created in the
   * system.
   */
  private static final String CREATED_DATE = "createdDate";

  /**
   * Represents the identifier of the user who last modified an entity.
   *
   * <p>This constant is typically used as the field name for auditing purposes to store or retrieve
   * the user responsible for the most recent modification of an entity within the application.
   *
   * <p>It is primarily used in conjunction with event listeners or auditing mechanisms to provide
   * traceability and accountability for changes made to entities.
   */
  private static final String LAST_MODIFIED_BY = "lastModifiedBy";

  /**
   * Represents the name of the auditing field used to store the timestamp of the last modification
   * made to an entity. This constant is used primarily for referencing the field name
   * programmatically in auditing processes or reflective operations.
   *
   * <p>The field typically holds a date or timestamp value indicating when the entity was last
   * updated.
   */
  private static final String LAST_MODIFIED_DATE = "lastModifiedDate";

  private final AuditorAware<User> userAuditorAware;

  /**
   * Sets the creation-related auditing fields for the given target entity. This method is invoked
   * before persisting the entity to ensure that fields such as "createdDate" and "createdBy" are
   * populated with appropriate values.
   *
   * @param target the entity for which creation-specific auditing fields need to be set
   */
  @PrePersist
  public void setCreatedOn(final BaseEntity<?> target) {
    updateAuditFields(target, true);
  }

  /**
   * Sets or updates the auditing fields for the provided entity when an update operation occurs.
   * This method is triggered before the persistence context is updated with changes to the entity.
   *
   * @param target the entity for which the auditing fields (e.g., last modified date, last modified
   *     by) should be set or updated
   */
  @PreUpdate
  public void setUpdatedOn(final BaseEntity<?> target) {
    updateAuditFields(target, false);
  }

  /**
   * Sets the value of a field in the provided {@code baseEntity} if it is annotated with specific
   * annotations such as {@link DeletedBy} or {@link DeletedDate}. Depending on the annotation
   * present, the field value is updated accordingly:
   *
   * <ul>
   *   <li>If annotated with {@link DeletedBy}, it is set to the current auditor's value.
   *   <li>If annotated with {@link DeletedDate}, it is set to the current timestamp. Logs warnings
   *       or throws exceptions in case of issues during the field update process.
   * </ul>
   *
   * @param baseEntity the entity object whose field needs to be updated based on annotation
   * @param declaredField the field object to be inspected and updated if annotated
   */
  private void setFieldIfAnnotated(
      final BaseEntity<?> baseEntity, @NonNull final Field declaredField) {
    try {
      if (declaredField.isAnnotationPresent(DeletedBy.class)) {
        final Optional<User> auditor = userAuditorAware.getCurrentAuditor();
        if (auditor.isPresent()) {
          setFieldValue(declaredField, baseEntity, auditor.get());
        } else {
          log.warn("Current auditor is null for deleted entity: {}", baseEntity);
        }
      } else if (declaredField.isAnnotationPresent(DeletedDate.class)) {
        setFieldValue(declaredField, baseEntity, Instant.now());
      }
    } catch (final RuntimeException exception) {
      log.error(
          "Error handling annotated fields for {}: {}",
          baseEntity,
          declaredField.getName(),
          exception);
      throw new EntityAccessException(
          MessageFormat.format(
              "Failed to set annotated field {0} for entity", declaredField.getName()),
          exception);
    }
  }

  /**
   * Updates the auditing fields for the specified entity. If the entity is marked as deleted and
   * the {@code deletedDate} is not set, it processes fields annotated with {@code DeletedBy} or
   * {@code DeletedDate} using reflection. Additionally, it sets fields for audit information such
   * as creation or modification timestamps and user details.
   *
   * @param baseEntity the entity for which auditing fields need to be updated
   * @param isCreate a flag indicating whether the operation is for entity creation. If {@code
   *     true}, creation-specific fields such as {@code createdDate} and {@code createdBy} are set
   */
  private void updateAuditFields(@NonNull final BaseEntity<?> baseEntity, final boolean isCreate) {
    if (baseEntity.isDeleted() && baseEntity.getDeletedDate().isEmpty()) {
      Class<?> current = baseEntity.getClass();
      while (null != current && Object.class != current) {
        Arrays.stream(current.getDeclaredFields())
            .forEach(field -> setFieldIfAnnotated(baseEntity, field));
        current = current.getSuperclass();
      }
    }

    final Optional<User> currentAuditor = userAuditorAware.getCurrentAuditor();
    if (currentAuditor.isEmpty()) {
      log.warn("Current auditor is null for entity: {}", baseEntity);
    }

    final LocalDateTime now = LocalDateTime.now();
    setIfFieldExists(baseEntity, LAST_MODIFIED_DATE, now);
    setIfFieldExists(baseEntity, LAST_MODIFIED_BY, currentAuditor.orElse(null));
    if (isCreate) {
      setIfFieldExists(baseEntity, CREATED_DATE, now);
      setIfFieldExists(baseEntity, CREATED_BY, currentAuditor.orElse(null));
    }
  }

  /**
   * Sets the value of the specified field on the provided target object using reflection. This
   * method attempts to invoke the appropriate setter method for the given field. If no suitable
   * setter is found or if an error occurs during invocation, an {@link EntityAccessException} is
   * thrown with an appropriate message.
   *
   * @param field the field to be set must not be null
   * @param target the object on which the field value is to be set must not be null
   * @param value the value to be set on the field can be null
   * @throws EntityAccessException if no setter method exists for the field, or if there is an error
   *     executing the setter
   */
  private static void setFieldValue(
      @NonNull final Field field, @NonNull final Object target, final Object value) {
    try {
      final String fieldName = field.getName();
      final String setterName =
          MessageFormat.format(
              "set{0}{1}", fieldName.substring(0, 1).toUpperCase(Locale.ROOT), fieldName.substring(1));
      final Method setter = target.getClass().getMethod(setterName, field.getType());
      setter.invoke(target, value);
    } catch (final NoSuchMethodException e) {
      log.error(
          "No setter found for field {} in class {}", field.getName(), target.getClass().getName());
      throw new EntityAccessException(
          MessageFormat.format("No setter found for field {0}", field.getName()), e);
    } catch (final IllegalAccessException | InvocationTargetException e) {
      log.error("Failed to invoke setter for field {} on entity: {}", field.getName(), target, e);
      throw new EntityAccessException(
          MessageFormat.format("Failed to invoke setter for field {0}", field.getName()), e);
    }
  }

  /**
   * Attempts to set the value of the given field on the provided entity only if the field exists in
   * the class hierarchy.
   *
   * @param baseEntity the entity containing the field
   * @param fieldName the name of the field to be set
   * @param value the value to set the field to
   */
  private static void setIfFieldExists(
      @NonNull final BaseEntity<?> baseEntity, final String fieldName, final Object value) {
    Class<?> current = baseEntity.getClass();
    while (null != current && Object.class != current) {
      try {
        final Field field = current.getDeclaredField(fieldName);
        setFieldValue(field, baseEntity, value);
        return; // Field found and set, exit
      } catch (final NoSuchFieldException ignored) {
        current = current.getSuperclass();
      }
    }
    log.warn("Field {} not found in class hierarchy for entity: {}", fieldName, baseEntity);
  }
}
