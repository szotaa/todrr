package pl.szotaa.todrr.task.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(long id) {
        super("Task with id: " + id + " not found");
    }
}
