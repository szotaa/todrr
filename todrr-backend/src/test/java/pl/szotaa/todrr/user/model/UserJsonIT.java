package pl.szotaa.todrr.user.model;

import java.time.Instant;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.junit4.SpringRunner;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@JsonTest
@RunWith(SpringRunner.class)
public class UserJsonIT {

    @Autowired
    JacksonTester<User> jacksonTester;

    @Test
    public void serialize_allFieldsProvided_jsonProperlySerialized() throws Exception {
        //given
        Instant now = Instant.now();

        User user = User.builder()
                .id(1L)
                .email("user@email.com")
                .emailActivationToken("token")
                .password("password")
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .tasks(Collections.emptyList())
                .build();

        //when
        JsonContent<User> jsonUser = jacksonTester.write(user);

        //then
        assertThat(jsonUser).hasJsonPathValue("@.id");
        assertThat(jsonUser).hasJsonPathValue("@.email");
        assertThat(jsonUser).hasJsonPathValue("@.role");
        assertThat(jsonUser).hasJsonPathValue("@.tasks");
        assertThat(jsonUser).hasJsonPathValue("@.isEnabled");
        assertThat(jsonUser).doesNotHaveJsonPathValue("@.password");
        assertThat(jsonUser).doesNotHaveJsonPathValue("@.emailActivationToken");
    }

    @Test
    public void deserialize_allFieldsProvided_jsonProperlyDeserialized() throws Exception {
        //given
        String jsonUser = "{\"id\": \"1\", \"email\": \"user@email.com\", \"emailActivationToken\": \"token\", \"password\": \"password\", \"role\": \"USER\", \"isEnabled\": \"true\"}";

        //when
        User user = jacksonTester.parseObject(jsonUser);

        //then
        assertEquals("user@email.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertNull(user.getEmailActivationToken());
        assertNull(user.getId());
        assertNull(user.getRole());
        assertNull(user.getIsEnabled());
    }
}