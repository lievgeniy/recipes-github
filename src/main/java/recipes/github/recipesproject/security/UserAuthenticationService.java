package recipes.github.recipesproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.github.recipesproject.business.model.User;
import recipes.github.recipesproject.persistence.repository.Interface.IUserRepository;

import java.util.Optional;

@Service
public class UserAuthenticationService implements UserDetailsService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(username);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found : " + username));
        return user.map(UserAuthentication::new).get();
    }
}