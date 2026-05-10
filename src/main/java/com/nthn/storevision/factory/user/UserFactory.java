package com.nthn.storevision.factory.user;

import com.nthn.storevision.model.User;
import org.springframework.lang.NonNull;
import java.util.Map;

public interface UserFactory {
  User create(@NonNull Map<String, Object> profile);
}
