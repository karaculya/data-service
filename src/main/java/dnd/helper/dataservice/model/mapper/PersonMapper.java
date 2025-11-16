package dnd.helper.dataservice.model.mapper;

import openapi.dto.Person;
import dnd.helper.dataservice.model.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {InventoryItemMapper.class, StatMapper.class}
)
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
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PersonEntity toEntity(Person dto);
}
