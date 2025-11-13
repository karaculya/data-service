package dnd.helper.dataservice.repository;

import dnd.helper.dataservice.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
