package pl.szotaa.todrr.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown on attempting to confirm email with wrong email confirmation token.
 *
 * @author szotaa
 */

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Invalid email confirmation token")
public class InvalidEmailConfirmationTokenException extends Exception {
}
