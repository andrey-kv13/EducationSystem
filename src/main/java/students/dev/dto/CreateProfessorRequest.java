package students.dev.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProfessorRequest(
        @NotBlank(message = "Имя обязательно")
        @NotNull
        String firstName,
        @NotBlank(message = "Фамилия обязательна")
        @NotNull(message = "")
        String lastName
) {}
