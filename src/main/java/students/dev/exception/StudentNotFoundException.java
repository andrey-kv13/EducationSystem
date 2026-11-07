package students.dev.exception;

public class StudentNotFoundException extends ResourceNotFoundException{
    public StudentNotFoundException(long id) {
        super("Студент с id " + id + " не найден");
    }
}
