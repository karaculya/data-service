package dnd.helper.dataservice.repository;

import dnd.helper.dataservice.model.entity.InventoryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository extends JpaRepository<InventoryItemEntity, Long> {
}
