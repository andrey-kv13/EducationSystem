package students.dev.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateScheduleRequest(
        @NotNull(message = "Id группы обязателен")
        Long groupId,
        @NotNull(message = "Id профессора обязателен")
        Long professorId,
        @NotNull(message = "Id курса обязателен")
        Long courseId,
        @NotNull(message = "Дата начала обучения обязательна")
        LocalDate startEducationDate,
        @NotNull(message = "Дата окончания обучения обязательна")
        LocalDate endEducationDate
) {
    @AssertTrue(message = "Дата окончания не может быть раньше даты начала")
    private boolean isEndDateNotBeforeStart() {
        if (startEducationDate == null || endEducationDate == null) {
            return true;
        }
        return !endEducationDate.isBefore(startEducationDate);
    }
}
