/*
 * Copyright (c) 2026 Hong Nhat. All rights reserved.
 *
 * Project: Store Vision
 * Module: Store Vision Main
 * File: 001-alter-users-table.sql
 * Author: Nguyen Thi Hong Nhat
 * Contact: hongnhat1121@gmail.com
 * Last Modified: 2026-06-03 17:45:01
 */

DO
$$
    BEGIN
        ALTER TABLE users
            DROP COLUMN IF EXISTS name;
        ALTER TABLE users
            DROP COLUMN IF EXISTS avatar_url;
        ALTER TABLE users
            DROP COLUMN IF EXISTS provider;
        ALTER TABLE users
            DROP COLUMN IF EXISTS provider_id;

        ALTER TABLE users
            ADD COLUMN IF NOT EXISTS first_name VARCHAR(45);
        ALTER TABLE users
            ADD COLUMN IF NOT EXISTS last_name VARCHAR(45);
        ALTER TABLE users
            ADD COLUMN IF NOT EXISTS password VARCHAR(255);
        ALTER TABLE users
            ADD COLUMN IF NOT EXISTS username VARCHAR(45);

        ALTER TABLE users
            ALTER COLUMN email TYPE VARCHAR(100);

        ALTER TABLE users
            ALTER COLUMN created_by DROP NOT NULL;
        ALTER TABLE users
            ALTER COLUMN last_modified_by DROP NOT NULL;

        INSERT INTO users (email, first_name, last_name, password, username,
                           created_at, last_modified_at)
        VALUES ('admin@storevision.local',
                'Admin',
                'StoreVision',
                '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
                'admin',
                NOW(),
                NOW())
        ON CONFLICT (email) DO NOTHING;

        UPDATE users
        SET created_by       = id,
            last_modified_by = id
        WHERE email = 'admin@storevision.local';

        UPDATE users
        SET first_name       = COALESCE(first_name, 'Unknown'),
            last_name        = COALESCE(last_name, 'User'),
            password         = COALESCE(password,
                                        '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy'),
            username         = COALESCE(username, email),
            created_by       = COALESCE(created_by, (SELECT id
                                                     FROM users
                                                     WHERE email = 'admin@storevision.local')),
            last_modified_by = COALESCE(last_modified_by, (SELECT id
                                                           FROM users
                                                           WHERE email = 'admin@storevision.local'))
        WHERE first_name IS NULL
           OR last_name IS NULL
           OR password IS NULL
           OR username IS NULL
           OR created_by IS NULL
           OR last_modified_by IS NULL;

        ALTER TABLE users
            ALTER COLUMN first_name SET NOT NULL;
        ALTER TABLE users
            ALTER COLUMN last_name SET NOT NULL;
        ALTER TABLE users
            ALTER COLUMN password SET NOT NULL;
        ALTER TABLE users
            ALTER COLUMN username SET NOT NULL;

        ALTER TABLE users
            ALTER COLUMN created_by SET NOT NULL;
        ALTER TABLE users
            ALTER COLUMN last_modified_by SET NOT NULL;

        IF NOT EXISTS (SELECT 1
                       FROM information_schema.table_constraints
                       WHERE table_schema = CURRENT_SCHEMA()
                         AND table_name = 'users'
                         AND constraint_name = 'uq_users_username') THEN
            ALTER TABLE users
                ADD CONSTRAINT uq_users_username UNIQUE (username);
        END IF;
    END
$$;
