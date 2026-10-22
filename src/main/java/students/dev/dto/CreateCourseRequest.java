package students.dev.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateCourseRequest(
        @NotBlank(message = "Имя курса обязательно")
        String name,
        @NotBlank(message = "Описание курса обязательно")
        String description,
        @NotNull(message = "Id профессора обязателен")
        Long professorId,
        @NotNull(message = "id группы обязателен")
        @NotEmpty(message = "В списке должен быть хотя бы один id")
        List<Long> groupIds
) {}
