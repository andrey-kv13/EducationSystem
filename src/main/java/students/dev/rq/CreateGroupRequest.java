package students.dev.rq;

import jakarta.validation.constraints.NotBlank;

public record CreateGroupRequest(
        @NotBlank(message = "Имя группы обязательно")
        String name
) {}
