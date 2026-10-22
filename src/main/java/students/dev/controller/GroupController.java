package students.dev.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import students.dev.dto.CreateGroupRequest;
import students.dev.dto.CreateGroupResponse;
import students.dev.service.GroupService;

@RequestMapping("/api/group")
@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public Page<CreateGroupResponse> getAllGroups(Pageable pageable) {
        return groupService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CreateGroupResponse getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateGroupResponse createGroup(@Valid @RequestBody CreateGroupRequest request) {
        return groupService.createGroup(request);
    }

    @PutMapping("/{id}")
    public CreateGroupResponse updateGroup(
            @PathVariable Long id,
            @Valid @RequestBody CreateGroupRequest request) {
        return groupService.updateGroup(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);

    }
}
