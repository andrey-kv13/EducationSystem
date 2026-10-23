package students.dev.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import students.dev.rq.CreateStudentRequest;
import students.dev.rs.CreateStudentResponse;
import students.dev.entity.Group;
import students.dev.entity.Student;
import students.dev.exception.GroupNotFoundException;
import students.dev.exception.StudentNotFoundException;
import students.dev.mapper.StudentMapper;
import students.dev.repository.GroupRepository;
import students.dev.repository.StudentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final StudentMapper studentMapper;

    private Student findStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Page<CreateStudentResponse> findAll(Pageable pageable) {
        return studentRepository.findAll(pageable).map(studentMapper::toResponse);
    }

    public CreateStudentResponse getStudentById(Long id) {

        return studentMapper.toResponse(findStudentById(id));
    }

    private void applyGroupIds(Student student, List<Long> groupIds) {
        for (Group group : student.getGroups()) {
            group.getStudent().remove(student);
        }
        student.getGroups().clear();
        for (Long groupId : groupIds) {
            Group group = groupRepository.findById(groupId).orElseThrow(() -> new GroupNotFoundException(groupId));
            student.getGroups().add(group);
            group.getStudent().add(student);
        }

    }
    @Transactional
    public CreateStudentResponse createStudent(CreateStudentRequest request) {
        Student student = studentMapper.toEntity(request);
        applyGroupIds(student, request.groupIds());
        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Transactional
    public CreateStudentResponse updateStudent(Long id, CreateStudentRequest request) {
        Student student = findStudentById(id);
        studentMapper.update(request, student);
        applyGroupIds(student, request.groupIds());
        return studentMapper.toResponse(studentRepository.save(student));
    }

    public void deleteStudent(Long id) {
        findStudentById(id);
        studentRepository.deleteById(id);
    }
}
