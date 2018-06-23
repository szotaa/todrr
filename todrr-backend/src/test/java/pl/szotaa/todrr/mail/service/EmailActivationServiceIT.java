package pl.szotaa.todrr.mail.service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import java.util.Optional;
import javax.mail.internet.MimeMessage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.szotaa.todrr.mail.exception.InvalidEmailConfirmationTokenException;
import pl.szotaa.todrr.user.model.User;
import pl.szotaa.todrr.user.repository.UserRepository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailActivationServiceIT {

    @Autowired
    private EmailActivationService emailActivationService;

    @MockBean
    private UserRepository userRepository;

    private GreenMail greenMail;

    private ArgumentCaptor<User> userArgCaptor = ArgumentCaptor.forClass(User.class);

    @Before
    public void init(){
        this.greenMail = new GreenMail(new ServerSetup(2525, null, "smtp"));
        this.greenMail.start();
    }

    @After
    public void cleanUp(){
        this.greenMail.stop();
    }

    @Test
    public void sendActivationEmail_emailGetsSent() throws Exception {
        //when
        emailActivationService.sendActivationEmail(1L, "user@email.com", "token");

        //then
        MimeMessage[] messages = greenMail.getReceivedMessages();
        assertEquals(1, messages.length);
        MimeMessage message = messages[0];
        assertEquals("Welcome to Toddr!", message.getSubject());
    }

    @Test
    public void activateAccount_accountGetsActivated() throws Exception {
        //given
        User user = User.builder()
                .email("user@email.com")
                .password("password")
                .isEnabled(false)
                .emailActivationToken("token")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        //when
        emailActivationService.activateAccount(1L, "token");

        //then
        verify(userRepository, times(1)).save(userArgCaptor.capture());
        User saved = userArgCaptor.getValue();
        assertTrue(saved.getIsEnabled());
        assertNull(saved.getEmailActivationToken());
    }

    @Test(expected = InvalidEmailConfirmationTokenException.class)
    public void activateAccount_badToken_exceptionThrown() throws Exception {
        //given
        User user = User.builder()
                .email("user@email.com")
                .password("password")
                .isEnabled(false)
                .emailActivationToken("tokenn")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        //when
        emailActivationService.activateAccount(1L, "token");
    }

    @Test
    public void generateEmailActivationToken_tokenReturned(){
        assertNotNull(emailActivationService.generateEmailActivationToken());
    }
}