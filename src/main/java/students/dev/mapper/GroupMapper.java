package students.dev.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import students.dev.dto.CreateGroupRequest;
import students.dev.dto.CreateGroupResponse;
import students.dev.entity.Group;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    CreateGroupResponse toResponse(Group group);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Group toEntity(CreateGroupRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "courses", ignore = true)
    void update(CreateGroupRequest request, @MappingTarget Group group);
}
