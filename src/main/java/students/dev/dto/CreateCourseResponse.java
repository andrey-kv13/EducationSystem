package students.dev.dto;

import java.util.List;

public record CreateCourseResponse(
        Long id,
        String name,
        String description,
        CourseProfessorSummary professor,
        List<CourseGroupSummary> group
){}
