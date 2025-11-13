package dnd.helper.dataservice.repository;

import dnd.helper.dataservice.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}
