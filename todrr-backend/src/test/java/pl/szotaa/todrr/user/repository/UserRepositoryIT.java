package pl.szotaa.todrr.user.repository;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.szotaa.todrr.user.model.Role;
import pl.szotaa.todrr.user.model.User;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsername_usernameExistent_correctUserReturned() throws Exception {
        //given
        User user = User.builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        entityManager.persistAndFlush(user);

        //when
        User foundUser = userRepository.findByUsername("username").get();

        //then
        assertEquals(user, foundUser);
    }

    @Test
    public void findByUsername_usernameNonExistent_emptyOptionalReturned() throws Exception {
        //when
        Optional<User> foundUserOptional = userRepository.findByUsername("username");

        //then
        assertFalse(foundUserOptional.isPresent());
    }

    @Test
    public void existsByUsername_usernameExistent_trueReturned() throws Exception {
        //given
        User user = User.builder()
                .username("username")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        entityManager.persistAndFlush(user);

        //when&then
        assertTrue(userRepository.existsByUsername("username"));
    }

    @Test
    public void existsByUsername_usernameNonExistent_falseReturned() throws Exception {
        assertFalse(userRepository.existsByUsername("username"));
    }
}