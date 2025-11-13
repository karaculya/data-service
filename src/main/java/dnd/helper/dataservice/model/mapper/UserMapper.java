package dnd.helper.dataservice.model.mapper;

import dnd.helper.dataservice.model.UserEntity;
import openapi.dto.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "id", target = "userId")
    @Mapping(target = "createdAt", expression = "java(Utils.toOffsetDateTime(userEntity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(Utils.toOffsetDateTime(userEntity.getUpdatedAt()))")
    User toDto(UserEntity userEntity);

    @Mapping(source = "userId", target = "id")
    @Mapping(target = "masterGames", ignore = true)
    @Mapping(target = "characters", ignore = true)
    @Mapping(target = "joinedGames", ignore = true)
    @Mapping(target = "createdAt", expression = "java(Utils.toLocalDateTime(userDto.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(Utils.toLocalDateTime(userDto.getUpdatedAt()))")
    UserEntity toEntity(User userDto);
}
