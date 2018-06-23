package pl.szotaa.todrr.auth.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import pl.szotaa.todrr.auth.util.JwtTokenUtil;
import pl.szotaa.todrr.user.model.User;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    public void getAuthenticatedJwtToken_tokenReturned(){
        //given
        User user = User.builder()
                .email("user@email.com")
                .password("password")
                .build();

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(mock(Authentication.class));
        when(jwtTokenUtil.getToken(any(Authentication.class))).thenReturn("jwtToken");

        //when
        String token = authenticationService.getAuthenticatedJwtToken(user);

        //then
        assertEquals("jwtToken", token);
    }
}