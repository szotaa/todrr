package pl.szotaa.todrr.mail.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.szotaa.todrr.mail.service.EmailActivationService;


import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class EmailActivationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailActivationService emailActivationService;

    @Test
    public void activateAccount_accountActivationServiceGetsCalled() throws Exception {
        mockMvc.perform(get("/api/activate/{id}/{token}", 1, "token"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("email confirmed successfully"));

        verify(emailActivationService, times(1)).activateAccount(anyLong(), anyString());
    }
}