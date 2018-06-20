package pl.szotaa.todrr.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when user with specified username is already present in the database.
 *
 * @author szotaa
 */

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Username already taken")
public class UsernameTakenException extends Exception {
}
