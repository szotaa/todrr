package pl.szotaa.todrr.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szotaa.todrr.user.exception.UsernameTakenException;
import pl.szotaa.todrr.user.model.User;
import pl.szotaa.todrr.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    /**
     * Rest controller resolving various User related requests.
     *
     * @author szotaa
     */

    private final UserService userService;

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> add(@RequestBody User user) throws UsernameTakenException {
        userService.save(user);
        return ResponseEntity.ok().build();
    }
}
