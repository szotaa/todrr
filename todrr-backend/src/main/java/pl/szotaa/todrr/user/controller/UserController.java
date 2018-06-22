package pl.szotaa.todrr.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szotaa.todrr.user.exception.InvalidEmailConfirmationTokenException;
import pl.szotaa.todrr.user.exception.UserNotFoundException;
import pl.szotaa.todrr.user.exception.UsernameTakenException;
import pl.szotaa.todrr.user.model.User;
import pl.szotaa.todrr.user.service.EmailActivationService;
import pl.szotaa.todrr.user.service.UserService;

/**
 * Rest controller resolving various User related requests.
 *
 * @author szotaa
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final EmailActivationService emailActivationService;

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> add(@RequestBody User user) throws UsernameTakenException {
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/confirm/{id}/{confirmationToken}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> confirmEmail(@PathVariable Long id, @PathVariable String confirmationToken) throws UserNotFoundException, InvalidEmailConfirmationTokenException {
        emailActivationService.confirmEmail(id, confirmationToken);
        return ResponseEntity.ok("email confirmed successfully");
    }
}
