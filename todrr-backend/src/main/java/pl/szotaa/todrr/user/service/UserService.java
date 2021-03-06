package pl.szotaa.todrr.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.szotaa.todrr.mail.service.EmailActivationService;
import pl.szotaa.todrr.user.exception.UsernameTakenException;
import pl.szotaa.todrr.user.model.Role;
import pl.szotaa.todrr.user.model.User;
import pl.szotaa.todrr.user.repository.UserRepository;

/**
 * Task service providing CRUD functionality for User entity. Implements UserDetailsService for integration with
 * Spring Security.
 *
 * @author szotaa
 */

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailActivationService emailActivationService;

    public void save(User user) throws UsernameTakenException {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UsernameTakenException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsEnabled(false);
        user.setRole(Role.ROLE_USER);
        String emailActivationToken = emailActivationService.generateEmailActivationToken();
        user.setEmailActivationToken(emailActivationToken);
        userRepository.saveAndFlush(user);
        emailActivationService.sendActivationEmail(user.getId() ,user.getEmail(), emailActivationToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).<UsernameNotFoundException>orElseThrow(() -> {throw new UsernameNotFoundException(username);});
    }
}
