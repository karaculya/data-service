package dnd.helper.dataservice.repository;

import dnd.helper.dataservice.model.entity.StatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatRepository extends JpaRepository<StatEntity, Long> {
}
