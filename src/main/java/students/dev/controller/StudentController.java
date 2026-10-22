package students.dev.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import students.dev.dto.CreateStudentRequest;
import students.dev.dto.CreateStudentResponse;
import students.dev.service.StudentService;

@RequestMapping("/api/student")
@RestController
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public Page<CreateStudentResponse> findAll(Pageable pageable) {

        return studentService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CreateStudentResponse getById(@PathVariable Long id) {

        return studentService.getStudentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateStudentResponse createSchedule(@Valid @RequestBody CreateStudentRequest request) {
        return studentService.createStudent(request);

    }

    @PutMapping("/{id}")
    public CreateStudentResponse updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody CreateStudentRequest request) {
        return studentService.updateStudent(id,request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudentById(@PathVariable Long id) {

        studentService.deleteStudent(id);
    }
}
