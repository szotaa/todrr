package pl.szotaa.todrr.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.szotaa.todrr.user.exception.UsernameTakenException;
import pl.szotaa.todrr.user.model.User;
import pl.szotaa.todrr.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(User user) throws UsernameTakenException {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new UsernameTakenException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsEnabled(true);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).<UsernameNotFoundException>orElseThrow(() -> {throw new UsernameNotFoundException(username);});
    }
}
