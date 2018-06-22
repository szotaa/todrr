package pl.szotaa.todrr.user.service;

import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.szotaa.todrr.user.exception.UsernameTakenException;
import pl.szotaa.todrr.user.model.Role;
import pl.szotaa.todrr.user.model.User;
import pl.szotaa.todrr.user.repository.UserRepository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

    @Test
    public void save_newValidUser_userSaved() throws Exception {
        //given
        User user = User.builder()
                .email("email@email.com")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");

        //when
        userService.save(user);

        //then
        verify(userRepository, times(1)).save(userCaptor.capture());
        assertEquals(user, userCaptor.getValue());
    }

    @Test
    public void save_newValidUser_passwordGetsEncrypted() throws Exception {
        //given
        User user = User.builder()
                .email("email@email.com")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");

        //when
        userService.save(user);

        //then
        verify(userRepository, times(1)).save(userCaptor.capture());
        verify(passwordEncoder, times(1)).encode(anyString());
        User capturedUser = userCaptor.getValue();
        assertNotEquals("password", capturedUser.getPassword());
        assertEquals("encryptedPassword", capturedUser.getPassword());

    }

    @Test(expected = UsernameTakenException.class)
    public void save_existingValidUser_exceptionThrown() throws Exception {
        //given
        User user = User.builder()
                .email("email@email.com")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        //when&then
        userService.save(user);
    }

    @Test
    public void loadUserByUsername_usernameExisting_userReturned() throws Exception {
        //given
        User user = User.builder()
                .email("email@email.com")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        //when
        User loaded = (User) userService.loadUserByUsername("username");

        //then
        assertEquals(user, loaded);
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_usernameNotExisting_exceptionThrown() throws Exception {
        //given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        //when&then
        userService.loadUserByUsername("username");
    }
}