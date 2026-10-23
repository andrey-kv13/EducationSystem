package students.dev.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import students.dev.rq.CreateStudentRequest;
import students.dev.rs.CreateStudentResponse;
import students.dev.entity.Student;

@Mapper(componentModel = "spring")

public interface StudentMapper {

    CreateStudentResponse toResponse(Student student);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groups", ignore = true)
    Student toEntity(CreateStudentRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groups", ignore = true)
    void update(CreateStudentRequest request, @MappingTarget Student student);


}
