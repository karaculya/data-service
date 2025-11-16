package dnd.helper.dataservice.repository;

import dnd.helper.dataservice.model.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GameRepository extends JpaRepository<GameEntity, Long>, JpaSpecificationExecutor<GameEntity> {
}
