package pl.szotaa.todrr.bootstrap;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.szotaa.todrr.user.model.Role;
import pl.szotaa.todrr.user.model.User;
import pl.szotaa.todrr.user.repository.UserRepository;

/**
 * Populates database with fake data for development process convenience.
 *
 * @author szotaa
 */

@Component
@Profile("dev")
@RequiredArgsConstructor
public class MockDataDatabasePopulater {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Populates user table with mock users so there is no need to go through registration process
     * every time you need to test something requiring authentication.
     */

    @PostConstruct
    public void putMockUserAccounts() {
        User admin = User.builder()
                .email("admin@email.com")
                .password(passwordEncoder.encode("password"))
                .isEnabled(true)
                .role(Role.ROLE_ADMIN)
                .build();

        User user = User.builder()
                .email("user@email.com")
                .password(passwordEncoder.encode("password"))
                .isEnabled(true)
                .role(Role.ROLE_USER)
                .build();

        User disabledUser = User.builder()
                .email("disabled@email.com")
                .password(passwordEncoder.encode("password"))
                .isEnabled(false)
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(admin);
        userRepository.save(user);
        userRepository.save(disabledUser);
        userRepository.flush();
    }
}
