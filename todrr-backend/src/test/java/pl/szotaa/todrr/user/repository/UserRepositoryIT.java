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
    public void findByEmail_emailExistent_correctUserReturned() {
        //given
        User user = User.builder()
                .email("email@email.com")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        entityManager.persistAndFlush(user);

        //when
        User foundUser = userRepository.findByEmail("email@email.com").get();

        //then
        assertEquals(user, foundUser);
    }

    @Test
    public void findByEmail_emailNonExistent_emptyOptionalReturned(){
        //when
        Optional<User> foundUserOptional = userRepository.findByEmail("email@email.com");

        //then
        assertFalse(foundUserOptional.isPresent());
    }

    @Test
    public void existsByEmail_emailExistent_trueReturned(){
        //given
        User user = User.builder()
                .email("email@email.com")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        entityManager.persistAndFlush(user);

        //when&then
        assertTrue(userRepository.existsByEmail("email@email.com"));
    }

    @Test
    public void existsByEmail_emailNonExistent_falseReturned(){
        assertFalse(userRepository.existsByEmail("email@email.com"));
    }
}