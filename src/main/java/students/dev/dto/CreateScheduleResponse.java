package students.dev.dto;

import java.time.LocalDate;

public record CreateScheduleResponse(
        Long id,
        Long groupId,
        String groupName,
        Long professorId,
        String professorFirstName,
        String professorLastName,
        Long courseId,
        String courseName,
        LocalDate startEducationDate,
        LocalDate endEducationDate
) {}
