package pl.szotaa.todrr.user.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * User roles.
 *
 * @author szotaa
 */

public enum Role implements GrantedAuthority {

    ROLE_ADMIN, ROLE_USER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
