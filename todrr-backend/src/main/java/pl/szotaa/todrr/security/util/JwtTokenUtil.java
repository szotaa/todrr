package pl.szotaa.todrr.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pl.szotaa.todrr.user.model.User;

@Component
public class JwtTokenUtil {

    @Value("${jwt.expiration}")
    private long expirationTimeMs;

    @Value("${jwt.secret}")
    private String jwtSecret;

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

    public String getUsernameFromJwt(String token){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
