package pl.szotaa.todrr.auth.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.szotaa.todrr.auth.util.JwtTokenUtil;


import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AuthenticationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    public void attemptLoggingIn_logInSuccessful_jwtTokenReturned() throws Exception {
        //given
        String userJson = "{\"username\": \"exampleUsername\", \"password\":\"examplePassword\"}";
        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(null, null, null));
        when(jwtTokenUtil.getToken(any(Authentication.class))).thenReturn("jwtToken");

        //when&then
        mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("jwtToken"));

        verify(authenticationManager, times(1)).authenticate(any(Authentication.class));
        verify(jwtTokenUtil, times(1)).getToken(any(Authentication.class));
    }
}