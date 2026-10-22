package students.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import students.dev.entity.Professor;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByFirstNameAndLastName(String firstName, String lastName);
}
