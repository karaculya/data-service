package dnd.helper.dataservice.model.mapper;

import dnd.helper.dataservice.model.GameEntity;
import openapi.dto.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GameMapper {

    @Mapping(source = "master.id", target = "masterId")
    @Mapping(target = "createdAt", expression = "java(Utils.toOffsetDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(Utils.toOffsetDateTime(entity.getUpdatedAt()))")
    @Mapping(target = "nextSession", expression = "java(Utils.toOffsetDateTime(entity.getNextSession()))")
    Game toDto(GameEntity entity);

    @Mapping(source = "masterId", target = "master.id")
    @Mapping(target = "players", ignore = true)
    @Mapping(target = "createdAt", expression = "java(Utils.toLocalDateTime(dto.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(Utils.toLocalDateTime(dto.getUpdatedAt()))")
    @Mapping(target = "nextSession", expression = "java(Utils.toLocalDateTime(dto.getNextSession()))")
    GameEntity toEntity(Game dto);
}
