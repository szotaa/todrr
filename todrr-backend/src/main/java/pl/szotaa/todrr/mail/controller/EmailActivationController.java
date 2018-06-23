package pl.szotaa.todrr.mail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.szotaa.todrr.mail.exception.InvalidEmailConfirmationTokenException;
import pl.szotaa.todrr.mail.service.EmailActivationService;
import pl.szotaa.todrr.user.exception.UserNotFoundException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/activate")
public class EmailActivationController {

    private final EmailActivationService emailActivationService;

    @GetMapping("/{id}/{activationToken}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> activateAccount(@PathVariable Long id, @PathVariable String activationToken) throws UserNotFoundException, InvalidEmailConfirmationTokenException {
        emailActivationService.confirmEmail(id, activationToken);
        return ResponseEntity.ok("email confirmed successfully");
    }

}
