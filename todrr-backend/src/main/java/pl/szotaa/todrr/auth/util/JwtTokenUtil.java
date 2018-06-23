package pl.szotaa.todrr.auth.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.szotaa.todrr.user.model.User;

/**
 * Utility class for managing JWT tokens.
 *
 * @author szotaa
 */

@Component
public class JwtTokenUtil {

    /**
     * Token expiration time expressed in milliseconds.
     */

    @Value("${jwt.expiration}")
    private long expirationTimeMs;

    /**
     * Secret used for signing JWT tokens.
     */

    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Generates JWT token for user specified in Authentication object.
     * @param auth Spring's Security Authentication object.
     * @return Signed JWT token.
     */

    public String getToken(Authentication auth){
        User user = (User) auth.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTimeMs);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Decrypts username from JWT token.
     * @param token JWT token.
     * @return Username.
     */

    public String getUsernameFromJwt(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
