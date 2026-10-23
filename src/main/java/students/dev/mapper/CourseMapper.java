package students.dev.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import students.dev.rs.CourseProfessorSummary;
import students.dev.rq.CreateCourseRequest;
import students.dev.rs.CreateCourseResponse;
import students.dev.entity.Course;
import students.dev.entity.Professor;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "group", source = "studyGroups")
    CreateCourseResponse toResponse(Course course);

    @Mapping(target = "professorId", source = "id")
    CourseProfessorSummary toProfessorSummary(Professor professor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "professor", ignore = true)
    @Mapping(target = "studyGroups", ignore = true)
    Course toEntity(CreateCourseRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "professor", ignore = true)
    @Mapping(target = "studyGroups", ignore = true)
    void update(CreateCourseRequest request, @MappingTarget Course course);
}
