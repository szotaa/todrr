package pl.szotaa.todrr.task.exception;

/**
 * Exception thrown when task cannot be found in the database.
 *
 * @author szotaa
 */

public class TaskNotFoundException extends RuntimeException {

    /**
     * @param id id of task which couldn't be found.
     */

    public TaskNotFoundException(long id) {
        super("Task with id: " + id + " not found");
    }
}
