package students.dev.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import students.dev.dto.CreateProfessorRequest;
import students.dev.dto.CreateProfessorResponse;
import students.dev.entity.Professor;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {

    CreateProfessorResponse toResponse(Professor professor);

    @Mapping(target = "id", ignore = true)
    Professor toEntity(CreateProfessorRequest request);

    @Mapping(target = "id", ignore = true)
    void update(CreateProfessorRequest request, @MappingTarget Professor professor);
}
