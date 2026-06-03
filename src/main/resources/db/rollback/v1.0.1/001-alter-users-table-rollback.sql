DO
$$
    BEGIN
        -- Remove new columns
        ALTER TABLE users DROP COLUMN IF EXISTS first_name;
        ALTER TABLE users DROP COLUMN IF EXISTS last_name;
        ALTER TABLE users DROP COLUMN IF EXISTS password;
        ALTER TABLE users DROP COLUMN IF EXISTS username;
        ALTER TABLE users DROP CONSTRAINT IF EXISTS uq_users_username;

        -- Restore email column size
        ALTER TABLE users ALTER COLUMN email TYPE VARCHAR(255);

        -- Restore obsolete columns
        ALTER TABLE users ADD COLUMN IF NOT EXISTS name         VARCHAR(255);
        ALTER TABLE users ADD COLUMN IF NOT EXISTS avatar_url   TEXT;
        ALTER TABLE users ADD COLUMN IF NOT EXISTS provider     VARCHAR(50);
        ALTER TABLE users ADD COLUMN IF NOT EXISTS provider_id  VARCHAR(255);

        -- Remove the admin seed user (drop NOT NULL temporarily for FK references)
        ALTER TABLE users ALTER COLUMN created_by       DROP NOT NULL;
        ALTER TABLE users ALTER COLUMN last_modified_by DROP NOT NULL;
        UPDATE users SET created_by = NULL, last_modified_by = NULL WHERE created_by = id;
        DELETE FROM users WHERE email = 'admin@storevision.local';
        ALTER TABLE users ALTER COLUMN created_by       SET NOT NULL;
        ALTER TABLE users ALTER COLUMN last_modified_by SET NOT NULL;
    END
$$;
