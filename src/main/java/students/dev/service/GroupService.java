package students.dev.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import students.dev.dto.CreateGroupRequest;
import students.dev.dto.CreateGroupResponse;
import students.dev.entity.Group;
import students.dev.exception.GroupNotFoundException;
import students.dev.mapper.GroupMapper;
import students.dev.repository.GroupRepository;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public Page<CreateGroupResponse> findAll(Pageable pageable) {
        return groupRepository.findAll(pageable).map(groupMapper::toResponse);

    }

    private Group findGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException(id));
    }

    public CreateGroupResponse getGroupById(Long id) {

        return groupMapper.toResponse(findGroupById(id));

    }

    public CreateGroupResponse createGroup(CreateGroupRequest request) {
        Group group = groupMapper.toEntity(request);
        return groupMapper.toResponse(groupRepository.save(group));
    }

    public CreateGroupResponse updateGroup(Long id, CreateGroupRequest request) {
        Group group = findGroupById(id);
        groupMapper.update(request, group);
        return groupMapper.toResponse(groupRepository.save(group));
    }

    public void deleteGroup(Long id) {
        Group group = findGroupById(id);
        groupRepository.delete(group);
    }
}
