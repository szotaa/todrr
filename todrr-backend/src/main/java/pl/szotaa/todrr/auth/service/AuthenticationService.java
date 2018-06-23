package pl.szotaa.todrr.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.szotaa.todrr.auth.util.JwtTokenUtil;
import pl.szotaa.todrr.user.model.User;

/**
 * Authentication service.
 *
 * @author szotaa
 */

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * Generates valid JWT token when provided correct credentials.
     * @param user Object containing authentication credentials.
     * @return Authenticated JWT token on successful authentication.
     */

    public String getAuthenticatedJwtToken(User user){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                ));

        return jwtTokenUtil.getToken(authentication);
    }
}
