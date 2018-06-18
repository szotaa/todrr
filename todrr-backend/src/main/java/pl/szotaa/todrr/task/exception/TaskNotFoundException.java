package pl.szotaa.todrr.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when task cannot be found in the database.
 *
 * @author szotaa
 */

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Task with specified id can not be found.")
public class TaskNotFoundException extends RuntimeException {
}
