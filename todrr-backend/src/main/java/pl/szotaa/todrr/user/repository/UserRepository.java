package pl.szotaa.todrr.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.szotaa.todrr.user.model.User;

/**
 * Spring's Data JPA repository providing User's persistence functionality.
 *
 * @author szotaa
 */

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
