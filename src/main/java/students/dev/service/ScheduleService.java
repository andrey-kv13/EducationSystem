package students.dev.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import students.dev.dto.CreateScheduleRequest;
import students.dev.dto.CreateScheduleResponse;
import students.dev.entity.Course;
import students.dev.entity.Group;
import students.dev.entity.Professor;
import students.dev.entity.Schedule;
import students.dev.exception.CourseNotFoundException;
import students.dev.exception.GroupNotFoundException;
import students.dev.exception.ProfessorNotFoundException;
import students.dev.exception.ScheduleNotFoundException;
import students.dev.mapper.ScheduleMapper;
import students.dev.repository.CourseRepository;
import students.dev.repository.GroupRepository;
import students.dev.repository.ProfessorRepository;
import students.dev.repository.ScheduleRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GroupRepository groupRepository;
    private final ProfessorRepository professorRepository;
    private final CourseRepository courseRepository;
    private final ScheduleMapper scheduleMapper;


    private void applyRelations(Schedule schedule, CreateScheduleRequest request) {
        schedule.setGroup(findGroup(request.groupId()));
        schedule.setProfessor(findProfessor(request.professorId()));
        schedule.setCourse(findCourse(request.courseId()));
    }

    private Group findGroup(Long id) {
        return groupRepository.findById(id).orElseThrow(() -> new GroupNotFoundException(id));
    }

    private Professor findProfessor(Long id) {
        return professorRepository.findById(id).orElseThrow(() -> new ProfessorNotFoundException(id));
    }

    private Course findCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(id));
    }

    public Page<CreateScheduleResponse> findAll(Pageable pageable) {
        return scheduleRepository.findAll(pageable).map(scheduleMapper::toResponse);
    }

    private Schedule findScheduleById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException(id));
    }

    public CreateScheduleResponse getScheduleById(Long id) {
        return scheduleMapper.toResponse(findScheduleById(id));
    }

    public CreateScheduleResponse createSchedule(CreateScheduleRequest request) {
        Schedule schedule = scheduleMapper.toEntity(request);
        applyRelations(schedule, request);
        return scheduleMapper.toResponse(scheduleRepository.save(schedule));
    }

    public CreateScheduleResponse updateSchedule(Long id, CreateScheduleRequest request) {
        Schedule schedule = findScheduleById(id);
        scheduleMapper.update(request, schedule);
        applyRelations(schedule, request);
        return scheduleMapper.toResponse(scheduleRepository.save(schedule));
    }

    public void deleteSchedule(Long id) {
        Schedule schedule = findScheduleById(id);
        scheduleRepository.delete(schedule);
    }

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    public void  deleteExpiredSchedule() {
        LocalDate cutoff = LocalDate.now().minusYears(1);
        scheduleRepository.deleteByEndEducationDateBefore(cutoff);

    }

    public Page<CreateScheduleResponse> findSchedulesByGroupId(Long groupId, Pageable pageable) {
        findGroup(groupId);
        return scheduleRepository.findByGroupId(groupId, pageable).map(scheduleMapper::toResponse);
    }


    public Page<CreateScheduleResponse> findSchedulesByProfessorId(Long professorId, Pageable pageable) {
        findProfessor(professorId);
        return scheduleRepository.findByProfessorId(professorId, pageable).map(scheduleMapper::toResponse);
    }
}