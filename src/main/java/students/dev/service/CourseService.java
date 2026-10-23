package students.dev.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import students.dev.rq.CreateCourseRequest;
import students.dev.rs.CreateCourseResponse;
import students.dev.entity.Course;
import students.dev.entity.Group;
import students.dev.entity.Professor;
import students.dev.exception.CourseNotFoundException;
import students.dev.exception.GroupNotFoundException;
import students.dev.exception.ProfessorNotFoundException;
import students.dev.mapper.CourseMapper;
import students.dev.repository.CourseRepository;
import students.dev.repository.GroupRepository;
import students.dev.repository.ProfessorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final GroupRepository groupRepository;
    private final CourseMapper courseMapper;


    private Course findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() ->  new CourseNotFoundException(id));
    }

    public Page<CreateCourseResponse> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable).map(courseMapper::toResponse);
    }

    public CreateCourseResponse getCourseById(Long id) {
        return courseMapper.toResponse(findCourseById(id));
    }

    private void applyGroups(Course course, List<Long> groupIds) {
        for (Group group : course.getStudyGroups()) {
            group.getCourses().remove(course);
        }
        course.getStudyGroups().clear();

        for (Long groupId : groupIds) {
            Group group = groupRepository.findById(groupId)
                    .orElseThrow(() -> new GroupNotFoundException(groupId));
            course.getStudyGroups().add(group);
            group.getCourses().add(course);
        }

    }

    private Professor findProfessor(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(() -> new ProfessorNotFoundException(id));
    }

    @Transactional
    public CreateCourseResponse createCourse(CreateCourseRequest request) {
        Course course = courseMapper.toEntity(request);
        course.setProfessor(findProfessor(request.professorId()));
        applyGroups(course, request.groupIds());
        return courseMapper.toResponse(courseRepository.save(course));
    }

    @Transactional
    public CreateCourseResponse updateCourse(Long id, CreateCourseRequest request) {
        Course course = findCourseById(id);
        courseMapper.update(request, course);
        course.setProfessor(findProfessor(request.professorId()));
        applyGroups(course, request.groupIds());
        return courseMapper.toResponse(courseRepository.save(course));
    }

    public void deleteCourse(Long id) {
        Course course = findCourseById(id);
        courseRepository.delete(course);
    }
}