package dnd.helper.dataservice.model.mapper;

import dnd.helper.dataservice.model.StatEntity;
import openapi.dto.Stat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatMapper {
    Stat toDto(StatEntity stats);
    StatEntity toEntity(Stat stats);
}
