package students.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import students.dev.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
