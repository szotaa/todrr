package pl.szotaa.todrr.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown then user with specified id or email cannot be found in the database;
 *
 * @author szotaa
 */

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Specified user not found")
public class UserNotFoundException extends Exception {
}
