package students.dev.exception;

public class GroupNotFoundException extends ResourceNotFoundException{
    public GroupNotFoundException(Long id) {
        super("Группа с id " + id + " не найдена.");
    }
}
