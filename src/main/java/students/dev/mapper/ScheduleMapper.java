package students.dev.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import students.dev.dto.CreateScheduleRequest;
import students.dev.dto.CreateScheduleResponse;
import students.dev.entity.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "groupName", source = "group.name")
    @Mapping(target = "professorId", source = "professor.id")
    @Mapping(target = "professorFirstName", source = "professor.firstName")
    @Mapping(target = "professorLastName", source = "professor.lastName")
    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "courseName", source = "course.name")
    CreateScheduleResponse toResponse(Schedule schedule);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "professor", ignore = true)
    @Mapping(target = "course", ignore = true)
    Schedule toEntity(CreateScheduleRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "professor", ignore = true)
    @Mapping(target = "course", ignore = true)
    void update(CreateScheduleRequest request, @MappingTarget Schedule schedule);

}
