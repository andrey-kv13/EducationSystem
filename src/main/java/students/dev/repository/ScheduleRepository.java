package students.dev.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import students.dev.entity.Schedule;

import java.time.LocalDate;

public interface ScheduleRepository  extends JpaRepository<Schedule,Long> {
    Page<Schedule> findByGroupId(Long groupId, Pageable pageable);
    Page<Schedule> findByProfessorId(Long professorId, Pageable pageable);

    void deleteByEndEducationDateBefore(LocalDate date);

}
