package pl.szotaa.todrr.bootstrap;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.szotaa.todrr.task.model.Task;
import pl.szotaa.todrr.task.repository.TaskRepository;
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
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void persistMockData() {
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
                .emailActivationToken("token")
                .isEnabled(false)
                .role(Role.ROLE_USER)
                .build();

        Task task1 = Task.builder()
                .name("task1Name")
                .description("task1Desc")
                .owner(user)
                .build();

        Task task2 = Task.builder()
                .name("task2Name")
                .description("task2Desc")
                .owner(user)
                .build();

        Task task3 = Task.builder()
                .name("task3Name")
                .description("task3Desc")
                .owner(user)
                .build();

        userRepository.saveAll(Arrays.asList(user, admin, disabledUser));
        taskRepository.saveAll(Arrays.asList(task1, task2, task3));
        userRepository.flush();
    }
}
