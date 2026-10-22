package students.dev.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import students.dev.dto.CreateCourseRequest;
import students.dev.dto.CreateCourseResponse;
import students.dev.service.CourseService;

@RequestMapping("/api/course")
@RestController
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public Page<CreateCourseResponse> getAllGroups(Pageable pageable) {

        return courseService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CreateCourseResponse getCourseById(@PathVariable Long id) {

        return courseService.getCourseById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCourseResponse createGroup(@Valid @RequestBody CreateCourseRequest request) {
        return courseService.createCourse(request);
    }

    @PutMapping("/{id}")
    public CreateCourseResponse updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CreateCourseRequest request) {
        return courseService.updateCourse(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable Long id) {
        courseService.deleteCourse(id);

    }
}
