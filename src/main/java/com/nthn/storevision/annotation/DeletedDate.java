/*
 * Copyright (c) 2025 Hong Nhat. All rights reserved.
 *
 * Project: Book Thymeleaf
 * Module: Book Thymeleaf Main
 * File: DeletedDate.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 14:38, 30/03/2025
 */

package com.nthn.storevision.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code DeletedDate} annotation is used to mark a field in an entity class that represents the
 * date and time when the entity was deleted.
 *
 * <p>This annotation can be used in conjunction with auditing features to automatically track the
 * deletion timestamp of an entity.
 *
 * <p>It is typically applied to a field of type {@code java.time.OffsetDateTime} or similar
 * date/time types to store the deletion date.
 *
 * <p>This annotation is retained at runtime, allowing it to be accessed through reflection.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DeletedDate {}
