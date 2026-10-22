package students.dev.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import students.dev.dto.CreateScheduleRequest;
import students.dev.dto.CreateScheduleResponse;
import students.dev.service.ScheduleService;

@RequestMapping("/api/schedule")
@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public Page<CreateScheduleResponse> findAll(Pageable pageable) {

        return scheduleService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CreateScheduleResponse getById(@PathVariable Long id) {

        return scheduleService.getScheduleById(id);
    }

    @GetMapping("/group/{groupId}/schedule")
    public Page<CreateScheduleResponse> findScheduleByGroupId(@PathVariable Long groupId, Pageable pageable) {
        return scheduleService.findSchedulesByGroupId(groupId, pageable);
    }

    @GetMapping("/professor/{professorId}/schedule")
    public Page<CreateScheduleResponse> findByProfessorId(@PathVariable Long professorId, Pageable pageable) {
        return scheduleService.findSchedulesByProfessorId(professorId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateScheduleResponse createSchedule(@Valid @RequestBody CreateScheduleRequest request) {
        return scheduleService.createSchedule(request);

    }

    @PutMapping("/{id}")
    public CreateScheduleResponse updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody CreateScheduleRequest request) {
        return scheduleService.updateSchedule(id,request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchedule(@PathVariable Long id) {

        scheduleService.deleteSchedule(id);
    }
}
