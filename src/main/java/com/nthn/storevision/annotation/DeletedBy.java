/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: StoreVision.main
 * File: DeletedBy.java
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-15 21:33:52
 */

package com.nthn.storevision.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code DeletedBy} annotation is used to mark a field in an entity class that represents the
 * user or entity responsible for deleting the instance of that entity.
 *
 * <p>This annotation is typically used in conjunction with auditing features to automatically track
 * who performed the deletion of an entity.
 *
 * <p>It is commonly applied to a field of type {@code String} or similar types to store the
 * identifier (e.g., username or user ID) of the user who deleted the entity.
 *
 * <p>This annotation is retained at runtime, allowing it to be accessed through reflection.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DeletedBy {}
