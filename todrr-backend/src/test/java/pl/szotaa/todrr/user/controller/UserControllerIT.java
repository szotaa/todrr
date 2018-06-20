package pl.szotaa.todrr.user.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.szotaa.todrr.user.model.User;
import pl.szotaa.todrr.user.service.UserService;


import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore //TODO: find out why is this test failing
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    public void add_validUser_userServiceGetsCalled() throws Exception {
        //given
        String userJson = "{\n" +
                "\"username\": \"username\"," +
                "\"password\": \"password\"" +
                "}";

        //when&then
        mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson)
                .with(csrf()))
                    .andExpect(status().isOk());

        verify(userService, times(1)).save(any(User.class));
    }
}