package pl.szotaa.todrr.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szotaa.todrr.auth.service.AuthenticationService;
import pl.szotaa.todrr.user.model.User;

/**
 * Rest authentication endpoint.
 *
 * @author szotaa
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody User user){
        return ResponseEntity.ok(authenticationService.getAuthenticatedJwtToken(user));
    }
}
