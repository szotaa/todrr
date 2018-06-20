package pl.szotaa.todrr.user.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Class representing user entity.
 *
 * @author szotaa
 */

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    @Size(min = 5)
    private String username;

    @NotEmpty
    @Size(min = 8)
    private String password;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @NotNull
    private Boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(this.role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //account expiration not supported
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //account locking not supported
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //credentials expiry not supported
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }

        if(!(obj instanceof User)){
            return false;
        }

        User user = (User) obj;
        return this.username.equalsIgnoreCase(user.getUsername());
    }

    @Override
    public int hashCode() {
        int result = this.username.toLowerCase().hashCode();
        result *= 31;
        return result;
    }
}
