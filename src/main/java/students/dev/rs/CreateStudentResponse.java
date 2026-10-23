package students.dev.rs;

import java.util.List;

public record CreateStudentResponse(
        Long id,
        String firstName,
        String lastName,
        List<StudentGroupSummary> groups
) {}
