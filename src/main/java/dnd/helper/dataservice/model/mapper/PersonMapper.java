package dnd.helper.dataservice.model.mapper;

import openapi.dto.Person;
import dnd.helper.dataservice.model.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    @Mapping(source = "game.id", target = "gameId")
    @Mapping(source = "player.id", target = "playerId")
    @Mapping(source = "characterClass", target = "propertyClass")
    @Mapping(target = "createdAt", expression = "java(Utils.toOffsetDateTime(entity.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(Utils.toOffsetDateTime(entity.getUpdatedAt()))")
    Person toDto(PersonEntity entity);

    @Mapping(source = "gameId", target = "game.id")
    @Mapping(source = "playerId", target = "player.id")
    @Mapping(source = "propertyClass", target = "characterClass")
    @Mapping(target = "stats", ignore = true)
    @Mapping(target = "inventory", ignore = true)
    @Mapping(target = "createdAt", expression = "java(Utils.toLocalDateTime(dto.getCreatedAt()))")
    @Mapping(target = "updatedAt", expression = "java(Utils.toLocalDateTime(dto.getUpdatedAt()))")
    PersonEntity toEntity(Person dto);
}
