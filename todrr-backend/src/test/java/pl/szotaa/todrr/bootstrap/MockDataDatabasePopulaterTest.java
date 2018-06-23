package pl.szotaa.todrr.bootstrap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.szotaa.todrr.user.model.User;
import pl.szotaa.todrr.user.repository.UserRepository;


import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MockDataDatabasePopulaterTest {

    @InjectMocks
    private MockDataDatabasePopulater databasePopulater;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void putMockUserAccounts_databaseGetsPopulated(){
        databasePopulater.putMockUserAccounts();
        verify(userRepository, times(3)).save(any(User.class));
    }
}