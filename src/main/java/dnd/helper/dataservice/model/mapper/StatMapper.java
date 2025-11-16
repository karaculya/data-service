package dnd.helper.dataservice.model.mapper;

import dnd.helper.dataservice.model.entity.StatEntity;
import openapi.dto.Stat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatMapper {
    @Mapping(target = "name", expression = "java(Stat.NameEnum.fromValue(stats.getName()))")
    Stat toDto(StatEntity stats);
    @Mapping(target = "name", expression = "java(stats.getName().getValue())")
    StatEntity toEntity(Stat stats);
}
