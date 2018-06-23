package pl.szotaa.todrr.auth.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.szotaa.todrr.auth.util.JwtTokenUtil;
import pl.szotaa.todrr.user.model.User;
import pl.szotaa.todrr.user.service.UserService;

/**
 * Seeks for JWT token in every request. If present, validates it and if successful sets the Authentication object.
 *
 * @author szotaa
 */

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = getJwtTokenFromRequest(httpServletRequest);
        if(jwtToken != null && !jwtToken.isEmpty()){
            String username = jwtTokenUtil.getUsernameFromJwt(jwtToken);
            User user = (User) userService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * Helper method for retrieving (if available) JWT token in usable form from request's header.
     * @param request HTTP request possibly containing JWT token.
     * @return JWT token.
     */

    private String getJwtTokenFromRequest(HttpServletRequest request){
        String bearer = request.getHeader("Authorization");
        if(bearer != null && !bearer.isEmpty() && bearer.startsWith("Bearer ")){
            return bearer.substring(7, bearer.length());
        }
        return null;
    }
}
