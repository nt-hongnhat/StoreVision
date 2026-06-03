---
name: feedback_sql_do_block
description: Tất cả SQL trong Liquibase changeset phải được bọc trong DO $$ BEGIN ... END $$ block
metadata:
  type: feedback
---

Luôn đặt toàn bộ nội dung SQL của Liquibase changeset trong anonymous block PostgreSQL:

```sql
DO
$$
    BEGIN
        -- SQL ở đây
    END
$$;
```

**Why:** Đây là quy ước của dự án StoreVision cho tất cả changeset SQL file.

**How to apply:** Áp dụng cho mọi file trong `db/changeset/v{version}/` và `db/rollback/v{version}/`, bao gồm cả CREATE TABLE, CREATE INDEX, DROP TABLE, DROP INDEX.
