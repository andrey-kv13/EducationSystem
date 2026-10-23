package students.dev.exception;

public class ScheduleNotFoundException extends ResourceNotFoundException {

    public ScheduleNotFoundException(Long id) {

        super("Расписание с id  " + id + " не найдено.");

    }
}
