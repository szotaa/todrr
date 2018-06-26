package pl.szotaa.todrr.auth.util;

import io.jsonwebtoken.Jwts;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;
import pl.szotaa.todrr.user.model.User;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @Before
    public void init(){
        ReflectionTestUtils.setField(jwtTokenUtil, "expirationTimeMs", 3155692597470L);
        ReflectionTestUtils.setField(jwtTokenUtil, "jwtSecret", "secret");
    }

    @Test
    public void getToken_expirationTimeSet(){
        //given
        User user = User.builder()
                .email("user@email.com")
                .password("password")
                .isEnabled(true)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, "password");

        //when
        Date now = new Date();
        String token = jwtTokenUtil.getToken(authentication);

        //then
        Date expiration = Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        assertNotNull(expiration);
        assertTrue(expiration.after(now));
    }

    @Test
    public void getToken_subjectSet(){
        //given
        User user = User.builder()
                .email("user@email.com")
                .password("password")
                .isEnabled(true)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, "password");

        //when
        String token = jwtTokenUtil.getToken(authentication);

        //then
        assertEquals("user@email.com", Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    @Test
    public void getUsernameFromJwt_usernameRetrieved(){
        //given
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImlhdCI6MTUzMDAxOTI5OCwiZXhwIjo0Njg1NzExODk1fQ.ePxwbTfnWDAicoleMGdOHBdeu3j_BP9VwwOnm7MRvQ1bwteilpbZzRQedb2e5We_TlUm4d06ZK6wkrI36wu-hQ";

        //when
        String username = jwtTokenUtil.getUsernameFromJwt(token);

        //then
        assertEquals("user@email.com", username);
    }
}