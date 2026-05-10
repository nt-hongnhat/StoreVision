package com.nthn.storevision.mapper;

import com.nthn.storevision.entity.UserEntity;
import com.nthn.storevision.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

  UserEntity toEntity(User user);

  User toModel(UserEntity userEntity);
}
