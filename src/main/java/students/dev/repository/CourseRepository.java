package students.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import students.dev.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
