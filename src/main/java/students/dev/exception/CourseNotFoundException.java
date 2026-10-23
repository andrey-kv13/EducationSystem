package students.dev.exception;

public class CourseNotFoundException extends ResourceNotFoundException {
    public CourseNotFoundException(Long id) {

        super("Курс с id" + id + " не найден.");
    }
}
