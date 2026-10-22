package students.dev.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateGroupRequest(
        @NotBlank(message = "Имя группы обязательно")
        String name
) {}
