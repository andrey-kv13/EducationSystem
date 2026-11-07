package students.dev.exception;

public class ProfessorNotFoundException extends ResourceNotFoundException{
    public ProfessorNotFoundException(Long id) {
         super("Профессор с таким id " + id + " не найден");
    }

    public ProfessorNotFoundException(String firstName, String lastName) {
        super("Профессор " + firstName + " " + lastName + " не найден");
    }
}
