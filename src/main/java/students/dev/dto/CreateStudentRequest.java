package students.dev.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateStudentRequest(
        @NotBlank(message = "Имя студента обязательно")
        String firstName,
        @NotBlank(message = "Фамилия студента обязательна")
        String lastName,
        @NotNull(message = "Список групп обязателен")
        @NotEmpty(message = "У студента должна быть минимум одна группа")
        List<Long> groupIds
) {}
