package dnd.helper.dataservice.repository;

import dnd.helper.dataservice.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
