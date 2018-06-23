package pl.szotaa.todrr.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Class representing user entity.
 *
 * @author szotaa
 */

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    /**
     * Email is also an username.
     */

    @Email
    @NotEmpty
    @Size(min = 5)
    @Column(unique = true)
    private String email;

    @NotEmpty
    @Size(min = 8)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Role role;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isEnabled;

    @JsonIgnore
    private String emailActivationToken;

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
    public String getUsername() {
        return email;
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
        return this.email.equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        int result = this.email.hashCode();
        result *= 31;
        return result;
    }
}
