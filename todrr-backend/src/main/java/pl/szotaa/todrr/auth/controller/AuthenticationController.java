package pl.szotaa.todrr.auth.controller;

import java.util.Collections;
import java.util.Map;
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

    @PostMapping(value = "/login")
    public ResponseEntity<Map> logIn(@RequestBody User user){
        String token = authenticationService.getAuthenticatedJwtToken(user);
        return ResponseEntity.ok(Collections.singletonMap("jwtToken", token));
    }
}
