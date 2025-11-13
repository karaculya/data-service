package dnd.helper.dataservice.model.mapper;

import dnd.helper.dataservice.model.InventoryItemEntity;
import openapi.dto.InventoryItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryItemMapper {
    InventoryItem toDto(InventoryItemEntity inventory);
    InventoryItemEntity toEntity(InventoryItem inventory);
}
